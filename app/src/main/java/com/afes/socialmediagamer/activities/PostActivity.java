package com.afes.socialmediagamer.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.UploadTask;
import com.afes.socialmediagamer.R;
import com.afes.socialmediagamer.models.Post;
import com.afes.socialmediagamer.providers.AuthProvider;
import com.afes.socialmediagamer.providers.ImageProvider;
import com.afes.socialmediagamer.providers.PostProvider;
import com.afes.socialmediagamer.utils.FileUtil;
import com.afes.socialmediagamer.utils.ViewedMessageHelper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class PostActivity extends AppCompatActivity {

    ImageView mImageViewPost1;
    ImageView mImageViewPost2;
    File mImageFile;
    File mImageFile2;
    Button mButtonPost;
    ImageProvider mImageProvider;
    PostProvider mPostProvider;
    AuthProvider mAuthProvider;
    EditText mTextInputTitle;
    EditText mTextInputDescription;

    TextView mTextViewPC; //Translator - traductor
    TextView mTextViewPS4; //Teacher - profesor
    TextView mTextViewXBOX; //Architect - arquitecto
    TextView mTextViewNitendo; //Secretary -

    TextView mTextViewCarpenter; //carpintero
    TextView mTextViewCook; //cocinero
    TextView mTextViewLocksmith; //cerrajero
    TextView mTextViewLaundryman; //Lavandero
    TextView mTextViewMechanic; //mecanico
    TextView mTextViewBaker; //panadero
    TextView mTextViewBarber; //barbero
    TextView mTextViewWelder; //soldador
    TextView mTextViewPainter; //pintor
    TextView mTextViewTailor; //sastre
    TextView mTextViewDeliever; //repartidor
    TextView mTextViewVigilant; //vigilante
    TextView mTextViewAnimator; //animador
    TextView mTextViewWoodcutter; //leñador
    TextView mTextViewAnnouncer; //locutor
    TextView mTextViewEngineer; //ingeniero

    CircleImageView mCircleImageBack;
    TextView mTextViewCategory;
    String mCategory = "";
    String mTitle = "";
    String mDescription = "";
    AlertDialog mDialog;

    AlertDialog.Builder mBuilderSelector;
    CharSequence options[];
    private final int GALLERY_REQUEST_CODE = 1;
    private final int GALLERY_REQUEST_CODE_2 = 2;
    private final int PHOTO_REQUEST_CODE = 3;
    private final int PHOTO_REQUEST_CODE_2 = 4;

    // FOTO 1
    String mAbsolutePhotoPath;
    String mPhotoPath;
    File mPhotoFile;

    // FOTO 2
    String mAbsolutePhotoPath2;
    String mPhotoPath2;
    File mPhotoFile2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mImageProvider = new ImageProvider();
        mPostProvider = new PostProvider();
        mAuthProvider = new AuthProvider();

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();

        mBuilderSelector = new AlertDialog.Builder(this);
        mBuilderSelector.setTitle("Selecciona una opcion");
        options = new CharSequence[] {"Imagen de galeria", "Tomar foto"};

        mImageViewPost1 = findViewById(R.id.imageViewPost1);
        mImageViewPost2 = findViewById(R.id.imageViewPost2);
        mButtonPost = findViewById(R.id.btnPost);
        mTextInputTitle = findViewById(R.id.textInputVideoGame);
        mTextInputDescription = findViewById(R.id.textInputDescription);

        mTextViewPC = findViewById(R.id.imageViewPc);
        mTextViewPS4 = findViewById(R.id.imageViewPS4);
        mTextViewXBOX = findViewById(R.id.imageViewXbox);
        mTextViewNitendo = findViewById(R.id.imageViewNintendo);
        mTextViewCarpenter = findViewById(R.id.imageViewCarpintero);
        mTextViewCook = findViewById(R.id.imageViewCocinero);
        mTextViewLocksmith = findViewById(R.id.imageViewCerrajero);
        mTextViewLaundryman = findViewById(R.id.imageViewLanvandero);
        mTextViewMechanic = findViewById(R.id.imageViewMecanico);
        mTextViewBaker = findViewById(R.id.imageViewPanadero);
        mTextViewBarber = findViewById(R.id.imageViewBarbero);
        mTextViewWelder = findViewById(R.id.imageViewSoldador);
        mTextViewPainter = findViewById(R.id.imageViewPintor);
        mTextViewTailor = findViewById(R.id.imageViewSastre);
        mTextViewDeliever = findViewById(R.id.imageViewRepartidor);
        mTextViewVigilant = findViewById(R.id.imageViewVigilante);
        mTextViewAnimator = findViewById(R.id.imageViewAnimador);
        mTextViewWoodcutter = findViewById(R.id.imageViewLeñador);
        mTextViewAnnouncer = findViewById(R.id.imageViewLocutor);
        mTextViewEngineer = findViewById(R.id.imageViewIngeniero);

        mTextViewCategory = findViewById(R.id.textViewCategory);
        mCircleImageBack = findViewById(R.id.circleImageBack);

        mCircleImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPost();
            }
        });

        mImageViewPost1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectOptionImage(1);
            }
        });

        mImageViewPost2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectOptionImage(2);
            }
        });

        mTextViewPC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "PC";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewPS4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "PS4";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewXBOX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "XBOX";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewNitendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "NINTENDO";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewCarpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "CARPINTERO";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "COCINERO";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewLocksmith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "CERRAJERO";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewLaundryman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "LAVANDERO";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "MECANICO";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewBaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "PANADERO";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewBarber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "BARBERO";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewWelder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "SOLDADOR";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewPainter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "PINTOR";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewTailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "SASTRE";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewDeliever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "REPARTIDOR";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewVigilant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "VIGILANTE";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewAnimator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "ANIMADOR";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewWoodcutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "LEÑADOR";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewAnnouncer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "LOCUTOR";
                mTextViewCategory.setText(mCategory);
            }
        });

        mTextViewEngineer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCategory = "INGENIERO";
                mTextViewCategory.setText(mCategory);
            }
        });
    }

    private void selectOptionImage(final int numberImage) {

        mBuilderSelector.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    if (numberImage == 1) {
                        openGallery(GALLERY_REQUEST_CODE);
                    }
                    else if (numberImage == 2) {
                        openGallery(GALLERY_REQUEST_CODE_2);
                    }
                }
                else if (i == 1){
                    if (numberImage == 1) {
                        takePhoto(PHOTO_REQUEST_CODE);
                    }
                    else if (numberImage == 2) {
                        takePhoto(PHOTO_REQUEST_CODE_2);
                    }
                }
            }
        });

        mBuilderSelector.show();

    }

    @SuppressLint("QueryPermissionsNeeded")
    private void takePhoto(int requestCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createPhotoFile(requestCode);
            } catch(Exception e) {
                Toast.makeText(this, "Hubo un error con el archivo " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(PostActivity.this, "com.afes.socialmediagamer", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }

    private File createPhotoFile(int requestCode) throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photoFile = File.createTempFile(
            new Date() + "_photo",
            ".jpg",
            storageDir
        );
        if (requestCode == PHOTO_REQUEST_CODE) {
            mPhotoPath = "file:" + photoFile.getAbsolutePath();
            mAbsolutePhotoPath = photoFile.getAbsolutePath();
        }
        else if (requestCode == PHOTO_REQUEST_CODE_2) {
            mPhotoPath2 = "file:" + photoFile.getAbsolutePath();
            mAbsolutePhotoPath2 = photoFile.getAbsolutePath();
        }
        return photoFile;
    }


    private void clickPost() {
        
        mTitle = mTextInputTitle.getText().toString();
        mDescription = mTextInputDescription.getText().toString();
        if (!mTitle.isEmpty() && !mDescription.isEmpty() && !mCategory.isEmpty()) {
            // SELECCIONO AMBAS IMAGENES DE LA GALERIA
            if (mImageFile != null && mImageFile2 != null ) {
                saveImage(mImageFile, mImageFile2);
            }
            // TOMO LAS DOS FOTOS DE LA CAMARA
            else if (mPhotoFile != null && mPhotoFile2 != null) {
                saveImage(mPhotoFile, mPhotoFile2);
            }
            else if (mImageFile != null && mPhotoFile2 != null) {
                saveImage(mImageFile, mPhotoFile2);
            }
            else if (mPhotoFile != null && mImageFile2 != null) {
                saveImage(mPhotoFile, mImageFile2);
            }
            else {
                Toast.makeText(this, "Debes seleccionar una imagen", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Completa los campos para publicar", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage(File imageFile1, final File imageFile2) {
        mDialog.show();
        mImageProvider.save(PostActivity.this, imageFile1).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String url = uri.toString();

                            mImageProvider.save(PostActivity.this, imageFile2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> taskImage2) {
                                    if (taskImage2.isSuccessful()) {
                                        mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri2) {
                                                String url2 = uri2.toString();
                                                Post post = new Post();
                                                post.setImage1(url);
                                                post.setImage2(url2);
                                                post.setTitle(mTitle.toLowerCase());
                                                post.setDescription(mDescription);
                                                post.setCategory(mCategory);
                                                post.setIdUser(mAuthProvider.getUid());
                                                post.setTimestamp(new Date().getTime());
                                                mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> taskSave) {
                                                        mDialog.dismiss();
                                                        if (taskSave.isSuccessful()) {
                                                            clearForm();
                                                            Toast.makeText(PostActivity.this, "La informacion se almaceno correctamente", Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            Toast.makeText(PostActivity.this, "No se pudo almacenar la informacion", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                    else {
                                        mDialog.dismiss();
                                        Toast.makeText(PostActivity.this, "La imagen numero 2 no se pudo guardar", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
                else {
                    mDialog.dismiss();
                    Toast.makeText(PostActivity.this, "Hubo error al almacenar la imagen", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void clearForm() {
        mTextInputTitle.setText("");
        mTextInputDescription.setText("");
        mTextViewCategory.setText("CATEGORIAS");
        mImageViewPost1.setImageResource(R.drawable.upload_image);
        mImageViewPost2.setImageResource(R.drawable.upload_image);
        mTitle = "";
        mDescription = "";
        mCategory = "";
        mImageFile = null;
        mImageFile2 = null;
    }

    private void openGallery(int requestCode) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * SELECCION DE IMAGEN DESDE LA GALERIA
         */
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                mPhotoFile = null;
                mImageFile = FileUtil.from(this, data.getData());
                mImageViewPost1.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
            } catch(Exception e) {
                Log.d("ERROR", "Se produjo un error " + e.getMessage());
                Toast.makeText(this, "Se produjo un error " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        if (requestCode == GALLERY_REQUEST_CODE_2 && resultCode == RESULT_OK) {
            try {
                mPhotoFile2 = null;
                mImageFile2 = FileUtil.from(this, data.getData());
                mImageViewPost2.setImageBitmap(BitmapFactory.decodeFile(mImageFile2.getAbsolutePath()));
            } catch(Exception e) {
                Log.d("ERROR", "Se produjo un error " + e.getMessage());
                Toast.makeText(this, "Se produjo un error " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        /**
         * SELECCION DE FOTOGRAFIA
         */
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            mImageFile = null;
            mPhotoFile = new File(mAbsolutePhotoPath);
            Picasso.with(PostActivity.this).load(mPhotoPath).into(mImageViewPost1);
        }

        /**
         * SELECCION DE FOTOGRAFIA
         */
        if (requestCode == PHOTO_REQUEST_CODE_2 && resultCode == RESULT_OK) {
            mImageFile2 = null;
            mPhotoFile2 = new File(mAbsolutePhotoPath2);
            Picasso.with(PostActivity.this).load(mPhotoPath2).into(mImageViewPost2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ViewedMessageHelper.updateOnline(true, PostActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ViewedMessageHelper.updateOnline(false, PostActivity.this);
    }
}
