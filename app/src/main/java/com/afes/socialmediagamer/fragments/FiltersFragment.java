package com.afes.socialmediagamer.fragments;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afes.socialmediagamer.R;
import com.afes.socialmediagamer.activities.FiltersActivity;
import com.afes.socialmediagamer.adapters.PostsAdapter;
import com.afes.socialmediagamer.models.Post;
import com.afes.socialmediagamer.providers.AuthProvider;
import com.afes.socialmediagamer.providers.PostProvider;
import com.afes.socialmediagamer.utils.ViewedMessageHelper;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class FiltersFragment extends Fragment {

    View mView;
    LinearLayout mCardViewPS4;
    LinearLayout mCardViewXBOX;
    LinearLayout mCardViewNINTENDO;
    LinearLayout mCardViewPC;
    LinearLayout mCardViewCarpenter;
    LinearLayout mCardViewCook;
    LinearLayout mCardViewLocksmith;
    LinearLayout mCardViewLaunchdryman;
    LinearLayout mCardViewMechanic;
    LinearLayout mCardViewBaker;
    LinearLayout mCardViewBarber;
    LinearLayout mCardViewWelder;
    LinearLayout mCardViewPainter;
    LinearLayout mCardViewTailor;
    LinearLayout mCardViewDeliever;
    LinearLayout mCardViewVigilant;
    LinearLayout mCardViewAnimator;
    LinearLayout mCardViewWoodcutter;
    LinearLayout mCardViewAnnouncer;
    LinearLayout mCardViewEngineer;


    //Numero de resultados por publicacion
   // String mExtraCategory;
   // AuthProvider mAuthProvider;
   // RecyclerView mRecyclerView;
   // PostProvider mPostProvider;
   // PostsAdapter mPostsAdapter;
   // TextView mTextViewNumberFilter;


    public FiltersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_filters, container, false);

        mCardViewPS4 = mView.findViewById(R.id.cardViewPs4);
        mCardViewXBOX = mView.findViewById(R.id.cardViewXBOX);
        mCardViewNINTENDO = mView.findViewById(R.id.cardViewNINTENDO);
        mCardViewPC = mView.findViewById(R.id.cardViewPC);
        mCardViewCarpenter = mView.findViewById(R.id.CardViewCarpenter);
        mCardViewCook = mView.findViewById(R.id.CardViewCook);
        mCardViewLocksmith = mView.findViewById(R.id.CardViewLocksmith);
        mCardViewLaunchdryman = mView.findViewById(R.id.CardViewLaunchdryman);
        mCardViewMechanic = mView.findViewById(R.id.CardViewMechanic);
        mCardViewBaker = mView.findViewById(R.id.CardViewBaker);
        mCardViewBarber = mView.findViewById(R.id.CardViewBarber);
        mCardViewWelder = mView.findViewById(R.id.CardViewWelder);
        mCardViewPainter = mView.findViewById(R.id.CardViewPainter);
        mCardViewTailor = mView.findViewById(R.id.CardViewTailor);
        mCardViewDeliever = mView.findViewById(R.id.CardViewDeliever);
        mCardViewVigilant = mView.findViewById(R.id.CardViewVigilant);
        mCardViewAnimator = mView.findViewById(R.id.CardViewAnimator);
        mCardViewWoodcutter = mView.findViewById(R.id.CardViewWoodcutter);
        mCardViewAnnouncer = mView.findViewById(R.id.CardViewAnnouncer);
        mCardViewEngineer = mView.findViewById(R.id.CardViewEngineer);


       // mTextViewNumberFilter = mTextViewNumberFilter.findViewById(R.id.textViewNumberFilter);
       // mExtraCategory = getIntent().getStringExtra("category");
       // mAuthProvider = new AuthProvider();
       // mPostProvider = new PostProvider();

        mCardViewPS4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("PS4");
            }
        });

        mCardViewXBOX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("XBOX");
            }
        });

        mCardViewNINTENDO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("NINTENDO");
            }
        });

        mCardViewPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("PC");
            }
        });

        mCardViewCarpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("CARPINTERO");
            }
        });

        mCardViewCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("COCINERO");
            }
        });

        mCardViewLocksmith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("CERRAJERO");
            }
        });

        mCardViewLaunchdryman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("LAVANDERO");
            }
        });

        mCardViewMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("MECANICO");
            }
        });

        mCardViewBaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("PANADERO");
            }
        });

        mCardViewBarber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("BARBERO");
            }
        });

        mCardViewWelder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("SOLDADOR");
            }
        });

        mCardViewPainter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("PINTOR");
            }
        });

        mCardViewTailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("SASTRE");
            }
        });

        mCardViewDeliever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("REPARTIDOR");
            }
        });

        mCardViewVigilant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("VIGILANTE");
            }
        });

        mCardViewAnimator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("ANIMADOR");
            }
        });

        mCardViewWoodcutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("LEÑADOR");
            }
        });

        mCardViewAnnouncer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("LOCUTOR");
            }
        });

        mCardViewEngineer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToFilterActivity("INGENIERO");
            }
        });

        return mView;
    }

    private void goToFilterActivity(String category) {
        Intent intent = new Intent(getContext(), FiltersActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

  /*  @Override
    public void onStart() {
        super.onStart();
        Query query = mPostProvider.getPostByCategoryAndTimestamp(mExtraCategory);
        FirestoreRecyclerOptions<Post> options =
                new FirestoreRecyclerOptions.Builder<Post>()
                        .setQuery(query, Post.class)
                        .build();
        mPostsAdapter = new PostsAdapter(options, FiltersFragment.this, mTextViewNumberFilter);
        mRecyclerView.setAdapter(mPostsAdapter);
        mPostsAdapter.startListening();

    }*/


}
