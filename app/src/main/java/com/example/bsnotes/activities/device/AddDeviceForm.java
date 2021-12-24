package com.example.bsnotes.activities.device;

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

import com.example.bsnotes.activities.helperClasses.CheckInternetConnection;
import com.example.bsnotes.activities.models.HolderClass_Device_Info;
import com.example.bsnotes.activities.shops.ShopAddedSuccessfully;
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
import com.example.bsnotes.R;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class AddDeviceForm extends AppCompatActivity {
    TextInputLayout Devicename, WardenName, WardenEmail, ONTiming,
            OffTiming ,
            Password, ConfirmPassword, Devicecity, WardenPhoneno, Device_Description, ShopNumDevice;
    TextView locationInfo;
    ImageView DevicePreviewImage;
   Bitmap bitmap;
    String imageName;
    Uri ImageUrl;
    ProgressBar progressBar;
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
    private static final int REQUEST_CODE_IMAGEPICK = 4;
    String Cityselectedd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device_fom);
        firebaseAuth = FirebaseAuth.getInstance();
        PersonLoggedIn = firebaseAuth.getCurrentUser();
        CurrentUserUid = PersonLoggedIn.getUid();
        CurrentUserEmail = PersonLoggedIn.getEmail();
        mainproject = FirebaseDatabase.getInstance().getReference("device");
        firebaseDatabase = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        //Hooks
        spinner = (Spinner) findViewById(R.id.addhostelspinner);
        Devicename = (TextInputLayout) findViewById(R.id.devicename);
        Devicecity = (TextInputLayout) findViewById(R.id.devicecity);
         Device_Description = (TextInputLayout) findViewById(R.id.deviceDescription);
        ShopNumDevice = (TextInputLayout) findViewById(R.id.shopNumDevice);
        OffTiming = (TextInputLayout) findViewById(R.id.offTimingDevice);
        ONTiming = (TextInputLayout) findViewById(R.id.onTimingDevice);

        DevicePreviewImage = (ImageView) findViewById(R.id.devicepreviewImage);
        WardenEmail = (TextInputLayout) findViewById(R.id.shop_email);
        WardenName = (TextInputLayout) findViewById(R.id.addShop_fullname);
        Password = (TextInputLayout) findViewById(R.id.device_password);
        ConfirmPassword = (TextInputLayout) findViewById(R.id.device_confirmpassword);
        WardenPhoneno = (TextInputLayout) findViewById(R.id.shopphoneno);

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mainproject = FirebaseDatabase.getInstance().getReference("device");

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
    public void AddDeviceBtn(View view) {
        if (
                 !validateFullName()
                | !validateEmail()
                | !validatePassword()
                | !validateConfirmPassword()
                | !validateDescription()
                | !validateShopNum()
                | !validateImageview()
                | !validatePhone()
        ) {
            return;
        }
        CheckInternetConnection checkInternetConnection = new CheckInternetConnection();
        if (checkInternetConnection.isConnected(this)) {
            SignUpUser();
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
    public void BrowseDeviceImages(View view) {
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
                        startActivityForResult(Intent.createChooser(intent, "Select device Preview Image"), REQUEST_CODE_IMAGEPICK);
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
                DevicePreviewImage.setImageBitmap(bitmap);
                DevicePreviewImage.setVisibility(View.VISIBLE);
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
                relativeLayout.setVisibility(View.VISIBLE);
            } catch (Exception e) {
            }
        }
    }


    public boolean SignUpUser() {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Creating Account");
        dialog.setMessage("Please wait, Dont Quit");
        dialog.setCancelable(false);
        dialog.show();



        storageReference = storage.getReference("deviceImages" + new Random().nextInt(500));
        storageReference.putFile(ImageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                         HolderClass_Device_Info holderClass_hostel_info = new HolderClass_Device_Info(
                                 Device_Description.getEditText().getText().toString(),
                                 uri.toString(),
                                 Devicename.getEditText().getText().toString(),
                                 OffTiming.getEditText().getText().toString(),
                                 ONTiming.getEditText().getText().toString(),
                                 ShopNumDevice.getEditText().getText().toString(),
                                 Cityselectedd.toLowerCase(),
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
                                            Toast.makeText(AddDeviceForm.this, "Your device Information is Added", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddDeviceForm.this, "Sorry, Information Not Saved" + "\n" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddDeviceForm.this, "Records Not saved" + "\n" + e.toString(), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(AddDeviceForm.this, "Records Not saved" + "\n" + PersonLoggedIn.getUid(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                        ///////// now make all the fields blank
                        Devicename.getEditText().setText("");
                        WardenName.getEditText().setText("");
                        WardenEmail.getEditText().setText("");
                        Password.getEditText().setText("");
                        Devicecity.getEditText().setText("");
                        WardenPhoneno.getEditText().setText("");
                        Device_Description.getEditText().setText("");
                        OffTiming.getEditText().setText("");
                        ONTiming.getEditText().setText("");
                        ShopNumDevice.getEditText().setText("");
                        DevicePreviewImage.setImageResource(R.drawable.gallery_icon);
                        DevicePreviewImage.setVisibility(View.GONE);

                        ConfirmPassword.getEditText().setText("");
                        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
                        relativeLayout.setVisibility(View.GONE);


                        Toast.makeText(AddDeviceForm.this, "Process Finished", Toast.LENGTH_SHORT).show();
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
                dialog.setMessage("Adding device " + (int) percent + "%");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddDeviceForm.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        return true;
    }


    private boolean validateCity() {
        String city = Devicecity.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,20}\\z";
        if (city.isEmpty()) {
            Devicecity.setError("Field cannot be empty!");
            return false;
        } else {
            Devicecity.setError(null);
            return true;
        }
    }

    private boolean validateShopNum() {
        String description = ShopNumDevice.getEditText().getText().toString();
        if (description.isEmpty()) {
            ShopNumDevice.setError("Please write Shop Number");
            return false;
        }else {
            ShopNumDevice.setError(null);
            return true;
        }
    }
    private boolean validateDescription() {
        String description = Device_Description.getEditText().getText().toString();
        if (description.isEmpty()) {
            Device_Description.setError("Please write something about your hostel");
            return false;
        } else if (description.length() < 80) {
            Device_Description.setError("Description must be 80 letters long");
            return false;
        } else {
            Device_Description.setError(null);
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
        if (DevicePreviewImage.getDrawable() == null) {
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

    private boolean validateOnTiming() {
        String description = ONTiming.getEditText().getText().toString();
        if (description.isEmpty()) {
            ONTiming.setError("Please write A Shop OnTiming");
            return false;
        } else if (description.length() < 80) {
            ONTiming.setError("Description must be 80 letters long");
            return false;
        } else {
            ONTiming.setError(null);
            return true;
        }
    }

    private boolean validateOffTiming() {
        String description = OffTiming.getEditText().getText().toString();
        if (description.isEmpty()) {
            OffTiming.setError("Please write A Shop OffTiming");
            return false;
        } else if (description.length() < 80) {
            OffTiming.setError("Description must be 80 letters long");
            return false;
        } else {
            OffTiming.setError(null);
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
                        Toast.makeText(AddDeviceForm.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
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

    }


}