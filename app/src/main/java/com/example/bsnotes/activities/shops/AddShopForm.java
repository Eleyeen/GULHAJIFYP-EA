package com.example.bsnotes.activities.shops;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bsnotes.R;
import com.example.bsnotes.activities.helperClasses.CheckInternetConnection;
import com.example.bsnotes.activities.models.HolderClass_Shop_Info;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class AddShopForm extends AppCompatActivity {
    //////  defining views
    TextInputLayout Hostelname,
            WardenName,
            WardenEmail,
            Password,
            ConfirmPassword,
            Hostelcity,
            WardenPhoneno,
            Hostel_Description,
            ONTiming,
            OffTiming ,
            ShopNumAddForm;
    TextView locationInfo;
    ImageView hostelPreviewImage;
    Bitmap bitmap;
    String imageName;
    Uri ImageUrl;
    Spinner spinner;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage;
    FirebaseUser PersonLoggedIn;
    String CurrentUserUid;
    String CurrentUserEmail;
    FirebaseAuth firebaseAuth;
    DatabaseReference mainproject;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    //request code to pick images
    private static final int REQUEST_CODE_IMAGEPICK = 4;
    //position of selected Images
    int position = 0;
    String Cityselectedd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hostel_form);

        firebaseAuth = FirebaseAuth.getInstance();
        PersonLoggedIn = firebaseAuth.getCurrentUser();
        CurrentUserUid = PersonLoggedIn.getUid();
        CurrentUserEmail = PersonLoggedIn.getEmail();
        mainproject = FirebaseDatabase.getInstance().getReference("shops");
        firebaseDatabase = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        //Hooks
        ShopNumAddForm =(TextInputLayout) findViewById(R.id.shopNumAddForm);
        spinner = (Spinner) findViewById(R.id.addhostelspinnerAddForm);
        Hostelname = (TextInputLayout) findViewById(R.id.hostelname);
        Hostelcity = (TextInputLayout) findViewById(R.id.hostelcity);
        WardenPhoneno = (TextInputLayout) findViewById(R.id.wardenphoneno);
        Hostel_Description = (TextInputLayout) findViewById(R.id.hostelDescription);
        OffTiming = (TextInputLayout) findViewById(R.id.offTiming);
        ONTiming = (TextInputLayout) findViewById(R.id.onTiming);
        hostelPreviewImage = (ImageView) findViewById(R.id.hostelpreviewImage);
        locationInfo = (TextView) findViewById(R.id.locationInfo);
        WardenEmail = (TextInputLayout) findViewById(R.id.warden_email);
        WardenName = (TextInputLayout) findViewById(R.id.addHostel_wardenfullname);
        Password = (TextInputLayout) findViewById(R.id.warden_password);
        ConfirmPassword = (TextInputLayout) findViewById(R.id.warden_confirmpassword);
        WardenPhoneno = (TextInputLayout) findViewById(R.id.wardenphoneno);



        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mainproject = FirebaseDatabase.getInstance().getReference("shops");

        String ListOfCities[] = {"First Floor ","Second Floor","Third Floor "};


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListOfCities);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cityselectedd = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //////////// adding hostel button
    public void AddHostelBtn(View view) {
        if (!validateHostelName()
                | !validateFullName()
                | !validateEmail()
                | !validatePassword()
                | !validateConfirmPassword()
                | !validateDescription()
                | !validateImageview()
                | !validateShopNum()
//                | !validateOnTiming()
//                | !validateOffTiming()
                | !validatePhone()) {
            return;
        }
        CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
        if (checkInternetConnection.isConnected(this)) {
            ADDSHOP();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Oops")
                    .setCancelable(false)
                    .setMessage("No Internet Connection, please connect to internet to proceed")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Turn on", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                        }
                    }).show();
        }
        ////////// calling of sign up function and that also contains the image url and the rest of the hostel informations
    }

    // click handle for backbutton
    public void backButton(View view) {
        onBackPressed();
    }


    //click handle for pick images button
    public void BrowseImages(View view) {
        showGalleryIntent();
        /* RunTimeStoragePermission();*/
    }

    // User Defined Methods
    public void showGalleryIntent() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select Shops Preview Image"), REQUEST_CODE_IMAGEPICK);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        /* Toast.makeText(AddHostelForm.this, "Permission Denied!", Toast.LENGTH_SHORT).show();*/
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == REQUEST_CODE_IMAGEPICK && resultCode == RESULT_OK) {
            /*assert data != null;*/
            ImageUrl = data.getData();
            imageName = getFileName(ImageUrl);
            try {
                InputStream inputStream = getContentResolver().openInputStream(ImageUrl);
                bitmap = BitmapFactory.decodeStream(inputStream);
                hostelPreviewImage.setImageBitmap(bitmap);
                hostelPreviewImage.setVisibility(View.VISIBLE);
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
                relativeLayout.setVisibility(View.VISIBLE);
            } catch (Exception e) {
            }
        }
    }


    public void ADDHOSTEL() {

        //////// getting text
        String _HostelName = Hostelname.getEditText().getText().toString();
        String _Hostelcity = Hostelcity.getEditText().getText().toString().toUpperCase().trim();
        String _wardenphone = WardenPhoneno.getEditText().getText().toString().trim();
        String _HostelDescription = Hostel_Description.getEditText().getText().toString();
        String _OnTiming = ONTiming.getEditText().getText().toString();
        String _OffTiming = OffTiming.getEditText().getText().toString();
        String _ShopNUmAddForm = ShopNumAddForm.getEditText().getText().toString();

        String _wardenname = WardenName.getEditText().getText().toString();
        String _wardenpassword = Password.getEditText().getText().toString();
        String _wardenEmail = WardenEmail.getEditText().getText().toString();


        ////// dialog box....

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Adding Shops");
        dialog.setCancelable(false);
        dialog.show();


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference ImagesUploader = storage.getReference("shopImages" + new Random().nextInt(500));  ///// clear the random function and set .chile(getFileName);

    }


    public boolean ADDSHOP() {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Creating Account");
        dialog.setMessage("Please wait, Dont Quit");
        dialog.setCancelable(false);
        dialog.show();


        ///////////////////////////// code for adding picture

        storageReference = storage.getReference("shopImages" + new Random().nextInt(500));
        storageReference.putFile(ImageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        HolderClass_Shop_Info holderClass_hostel_info = new HolderClass_Shop_Info(
                                Cityselectedd.toLowerCase(),
                                Hostel_Description.getEditText().getText().toString(),
                                uri.toString(),

                                Hostelname.getEditText().getText().toString(),
                                OffTiming.getEditText().getText().toString(),

                                ONTiming.getEditText().getText().toString(),
                                ShopNumAddForm.getEditText().getText().toString(),
                                WardenEmail.getEditText().getText().toString(),

                                WardenName.getEditText().getText().toString(),
                                Password.getEditText().getText().toString(),
                                CurrentUserUid,
                                WardenPhoneno.getEditText().getText().toString()

                                );

                        mainproject.child(PersonLoggedIn.getUid()).setValue(holderClass_hostel_info)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AddShopForm.this, "Your Hostel Information is Added", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddShopForm.this, "Sorry, Information Not Saved" + "\n" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddShopForm.this, "Records Not saved" + "\n" + e.toString(), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(AddShopForm.this, "Records Not saved" + "\n" + PersonLoggedIn.getUid(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                        ///////// now make all the fields blank
                        Hostelname.getEditText().setText("");
                        WardenName.getEditText().setText("");
                        WardenEmail.getEditText().setText("");
                        Password.getEditText().setText("");
                        Hostelcity.getEditText().setText("");
                        WardenPhoneno.getEditText().setText("");
                        Hostel_Description.getEditText().setText("");
                        OffTiming.getEditText().setText("");
                        ONTiming.getEditText().setText("");
                        ShopNumAddForm.getEditText().setText("");

                        hostelPreviewImage.setImageResource(R.drawable.gallery_icon);
                        hostelPreviewImage.setVisibility(View.GONE);
                        locationInfo.setText("");
                        ConfirmPassword.getEditText().setText("");
                        locationInfo.setVisibility(View.GONE);
                        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
                        relativeLayout.setVisibility(View.GONE);


                        Toast.makeText(AddShopForm.this, "Process Finished", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                     
                        Intent intent = new Intent(getApplicationContext(), ShopAddedSuccessfully.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                dialog.setMessage("Adding Shops " + (int) percent + "%");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddShopForm.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        return true;
    }


    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    //////////// unused Methodsss /////////////////////////////////////////

    private void RunTimeStoragePermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        /*pickImagesIntent();*/
                        /* UplaodImages();*/

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(AddShopForm.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void pickImagesIntent() {

        //////// if the app is crashes plz remember to clear the following parameter .. ACTION_GET_CONTENT
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("images/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image(s)"), REQUEST_CODE_IMAGEPICK);

//////////////////////   Intent for gallery images selection Second Method//////////////////////////
       /* Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("images/*");
        startActivityForResult(intent, PICK_IMAGES_CODE);*/
////////////////////////////////////////////////////////////////////////////////////////////////////
    }

//    private void UplaodImages() {
//        // this is a dilog
//        final ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setTitle("Image(s) Uploading");
//        dialog.show();
//
//        //////// the following lines help us to uplaod images for firebase storage
//        /// we created a child named HostelImages and there im gonna set images stored in url
//        storageReference = storage.getReference().child("HostelImages");
//        storageReference.putFile(ImageUrl)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        //// show a toast of successfully uploaded
//                        Toast.makeText(AddShopForm.this, "Images Uploaded!", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//                })
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                        float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
//                        dialog.setMessage("Adding Your Hostel" + (int) percent + "%");
//                        ////////// write any thing you want to show to the user while uplaoding the pictures
//                    }
//                });
//    }
























    /////////// validation of the fields Hostel name , Hostel city , hostel description
    private boolean validateHostelName() {
        String HostelName = Hostelname.getEditText().getText().toString();
        if (HostelName.isEmpty()) {
            Hostelname.setError("Field cannot be empty!");
            return false;
        } else if (!HostelName.matches("[a-zA-Z ]+")) {
            Hostelname.setError("Enter Alphabetical characters only ");
            return false;

        } else if (!HostelName.contains("Shop")) {
            Hostelname.setError("This field should must contain the keyword 'Hostel'");
            return false;

        } else if (HostelName.length() > 30) {
            Hostelname.setError("Shop Name cannot be too large!");
            return false;
        } else {
            Hostelname.setError(null);
            return true;
        }
    }

    private boolean validateCity() {
        String city = Hostelcity.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";
        if (city.isEmpty()) {
            Hostelcity.setError("Field cannot be empty!");
            return false;
        } else {
            Hostelcity.setError(null);
            return true;
        }
    }

    private boolean validateDescription() {
        String description = Hostel_Description.getEditText().getText().toString();
        if (description.isEmpty()) {
            Hostel_Description.setError("Please write something about your hostel");
            return false;
        } else if (description.length() < 80) {
            Hostel_Description.setError("Description must be 80 letters long");
            return false;
        } else {
            Hostel_Description.setError(null);
            return true;
        }
    }


    private boolean validateOnTiming() {
        String description = ONTiming.getEditText().getText().toString();
        if (description.isEmpty()) {
            ONTiming.setError("Please write A Shop OnTiming");
            return false;
        }  else {
            ONTiming.setError(null);
            return true;
        }
    }

    private boolean validateOffTiming() {
        String description = OffTiming.getEditText().getText().toString();
        if (description.isEmpty()) {
            OffTiming.setError("Please write A Shop OffTiming");
            return false;
        }  else {
            OffTiming.setError(null);
            return true;
        }
    }

    private boolean validateShopNum() {
        String description = ShopNumAddForm.getEditText().getText().toString();
        if (description.isEmpty()) {
            ShopNumAddForm.setError("Please write Shop Number");
            return false;
        }else {
            ShopNumAddForm.setError(null);
            return true;
        }
    }

    private boolean validatePhone() {
        String phone = WardenPhoneno.getEditText().getText().toString().trim();
        String pattern = "[0-9]";
        if (phone.isEmpty()) {
            WardenPhoneno.setError("Please Enter Phone No");
            return false;
        } else if (phone.length() != 11) {
            WardenPhoneno.setError("Invalid Phone No");
            return false;
        }
         /*else if (!phone.matches(pattern)) {
            WardenPhoneno.setError("Only numbers are allowed ");
            return false;
        }*/
        else {
            WardenPhoneno.setError(null);
            return true;
        }
    }

    private boolean validateLatlng() {
        String latlang = locationInfo.getText().toString();
        if (latlang.isEmpty()) {
            Toast.makeText(this, "Choose Location", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            locationInfo.setError(null);
            return true;
        }
    }

    private boolean validateImageview() {
        if (hostelPreviewImage.getDrawable() == null) {
            Toast.makeText(this, "No Image selected", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateFullName() {
        String FullName = WardenName.getEditText().getText().toString().trim();
        if (FullName.isEmpty()) {
            WardenName.setError("Field cannot be empty");
            return false;
        } else {
            WardenName.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String UserEmail = WardenEmail.getEditText().getText().toString().trim();
        String checkemail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (UserEmail.isEmpty()) {
            WardenEmail.setError("Please Enter an email!");
            return false;
        } else if (!UserEmail.matches(checkemail)) {
            WardenEmail.setError("Invalid email!");
            return false;
        } else {
            WardenEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String UserPassword = Password.getEditText().getText().toString().trim();

        String checkpassword = "^" +
                "(?=.*[a-zA-Z])" +  //// any letter
                /* "(?=.*[@#$%^&+=])" +  //// atleast one special character*/
                "(?=\\S+$)" +        //// no white spaces
                ".{2,}" +            //// atleast 2 character
                "$";

        if (UserPassword.isEmpty()) {
            Password.setError("Please Choose a Password!");
            return false;
        } else if (UserPassword.length() < 6) {
            Password.setError("Password Is too Short");
            return false;
        } else if (!UserPassword.matches(checkpassword)) {
            Password.setError("Password must be contain 2 characters and any letter");
            return false;
        } else {
            Password.setError(null);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String cnfirmPassword = ConfirmPassword.getEditText().getText().toString().trim();
        String password = Password.getEditText().getText().toString().trim();
        /*  String checkpassword = "^" +
                "(?=.*[a-zA-Z])" +  //// any letter
                "(?=.*[@#$%^&+=])" +  //// atleast one special character
                "(?=\\S+$)" +        //// no white spaces
              ".{2,}" +            //// atleast 2 character
                "$";*/

        if (cnfirmPassword.isEmpty()) {
            ConfirmPassword.setError("Please Re-type Your password!");
            return false;
        } else if (cnfirmPassword.length() < 6) {
            ConfirmPassword.setError("Password too Short");
            return false;
        } else if (!cnfirmPassword.matches(password)) {
            ConfirmPassword.setError("Password doesn't match");
            return false;
        } else {
            ConfirmPassword.setError(null);
            return true;
        }
    }


}