package com.afes.socialmediagamer.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.afes.socialmediagamer.activities.HomeActivity;
import com.afes.socialmediagamer.activities.PostActivity;
import com.afes.socialmediagamer.fragments.HomeFragment;
import com.afes.socialmediagamer.providers.PostProvider;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.afes.socialmediagamer.R;
import com.afes.socialmediagamer.activities.PostDetailActivity;
import com.afes.socialmediagamer.models.Like;
import com.afes.socialmediagamer.models.Post;
import com.afes.socialmediagamer.providers.AuthProvider;
import com.afes.socialmediagamer.providers.LikesProvider;
import com.afes.socialmediagamer.providers.UsersProvider;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends FirestoreRecyclerAdapter<Post, PostsAdapter.ViewHolder> {

    Context context;
    UsersProvider mUsersProvider;
    UsersProvider mProfileProvider;
    UsersProvider mImageViewProfile;
    LikesProvider mLikesProvider;
    AuthProvider mAuthProvider;
    TextView mTextViewNumberFilter;
    ListenerRegistration mListener;

    public PostsAdapter(FirestoreRecyclerOptions<Post> options, Context context) {
        super(options);
        this.context = context;
        mUsersProvider = new UsersProvider();
        mProfileProvider = new UsersProvider();
        mImageViewProfile = new UsersProvider();
        mLikesProvider = new LikesProvider();
        mAuthProvider = new AuthProvider();
    }

    public PostsAdapter(FirestoreRecyclerOptions<Post> options, Context context, TextView textView) {
        super(options);
        this.context = context;
        mUsersProvider = new UsersProvider();
        mProfileProvider = new UsersProvider();
        mImageViewProfile = new UsersProvider();
        mLikesProvider = new LikesProvider();
        mAuthProvider = new AuthProvider();
        mTextViewNumberFilter = textView;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull final Post post) {

        DocumentSnapshot document = getSnapshots().getSnapshot(position);
        final String postId = document.getId();

        if (mTextViewNumberFilter != null) {
            int numberFilter = getSnapshots().size();
            mTextViewNumberFilter.setText(String.valueOf(numberFilter));
        }

        holder.textViewTitle.setText(post.getTitle());
       // holder.textViewDescription.setText(post.getDescription());

        if (post.getImage1() != null) {
            if (!post.getImage1().isEmpty()) {
                Picasso.with(context).load(post.getImage1()).into(holder.imageViewPost);

            }
        }
        if (post.getImageProfile() != null) {
            if (!post.getImageProfile().isEmpty()) {
                Picasso.with(context).load(post.getImageProfile()).into(holder.imageViewProfile);
            }
        }

        holder.viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostDetailActivity.class);
                intent.putExtra("id", postId);
                context.startActivity(intent);
            }
        });

        holder.imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Like like = new Like();
                like.setIdUser(mAuthProvider.getUid());
                like.setIdPost(postId);
                like.setTimestamp(new Date().getTime());
                like(like, holder);
            }
        });

        getUserInfo(post.getIdUser(), holder);
        getNumberLikesByPost(postId, holder);
        getProfileInfo(postId, holder);
        checkIfExistLike(postId, mAuthProvider.getUid(), holder);
    }


    private void getNumberLikesByPost(String idPost, final ViewHolder holder) {
        mListener = mLikesProvider.getLikesByPost(idPost).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    int numberLikes = queryDocumentSnapshots.size();
                    holder.textViewLikes.setText(String.valueOf(numberLikes) + " Me gustas");
                }
            }
        });
    }

    private void like(final Like like, final ViewHolder holder) {
        mLikesProvider.getLikeByPostAndUser(like.getIdPost(), mAuthProvider.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int numberDocuments = queryDocumentSnapshots.size();
                if (numberDocuments > 0) {
                    String idLike = queryDocumentSnapshots.getDocuments().get(0).getId();
                    holder.imageViewLike.setImageResource(R.drawable.ic_favorite_sinrelleno);
                    mLikesProvider.delete(idLike);
                }
                else {
                    holder.imageViewLike.setImageResource(R.drawable.ic_favorito_rojo_lleno);
                    mLikesProvider.create(like);
                }
            }
        });

    }

    private void checkIfExistLike(String idPost, String idUser, final ViewHolder holder) {
        mLikesProvider.getLikeByPostAndUser(idPost, idUser).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int numberDocuments = queryDocumentSnapshots.size();
                if (numberDocuments > 0) {
                    holder.imageViewLike.setImageResource(R.drawable.ic_favorito_rojo_lleno);
                }
                else {
                    holder.imageViewLike.setImageResource(R.drawable.ic_favorite_sinrelleno);
                }
            }
        });

    }

    private void getUserInfo(String idUser, final ViewHolder holder) {
        mUsersProvider.getUser(idUser).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    if (documentSnapshot.contains("username")) {
                        String username = documentSnapshot.getString("username");
                        holder.textViewUsername.setText(username);
                    }
                }
            }
        });

    }

    private void getProfileInfo(String idUser, ViewHolder holder) {
        mImageViewProfile.getUser(idUser).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    if (documentSnapshot.contains("image_profile")) {
                        String imageProfile = documentSnapshot.getString("image_profile");
                    }
                }
            }
        });
    }


    public ListenerRegistration getListener() {
        return mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_post, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        //TextView textViewDescription;
        TextView textViewUsername;
        TextView textViewLikes;
        ImageView imageViewPost;
        ImageView imageViewLike;
        ImageView imageViewProfile;
        CircleImageView circleImageView;
        View viewHolder;

        public ViewHolder(View view) {
            super(view);
            textViewTitle = view.findViewById(R.id.textViewTitlePostCard);
           // textViewDescription = view.findViewById(R.id.textViewDescriptionPostCard);
            textViewUsername = view.findViewById(R.id.textViewUsernamePostCard);
            textViewLikes = view.findViewById(R.id.textViewLikes);
            imageViewPost = view.findViewById(R.id.imageViewPostCard);
            imageViewLike = view.findViewById(R.id.imageViewLike);
            circleImageView = view.findViewById(R.id.circleImageProfilePost);
            viewHolder = view;
        }
    }

}
