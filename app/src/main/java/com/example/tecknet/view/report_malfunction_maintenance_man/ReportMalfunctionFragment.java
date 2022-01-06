package com.example.tecknet.view.report_malfunction_maintenance_man;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tecknet.controller.MaintenanceController;
import com.example.tecknet.databinding.FragmentReportMalfunctionMaintenanceManBinding;

import com.example.tecknet.model.ProductDetails;
import com.example.tecknet.model.UserInt;
import com.example.tecknet.view.UserViewModel;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class ReportMalfunctionFragment extends Fragment {

    private UserViewModel uViewModel;
    private FragmentReportMalfunctionMaintenanceManBinding binding;
    EditText detailFault;
    EditText device, type  ,company ;
    Button reportBut;
    ImageView malImage;
    ImageButton cameraBut;
    ImageButton galleryBut;

    private static final int MY_CAMERA_PERMISSION_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    String currectPhotoPath;

    View root;
    Spinner prodSpinner;

    ////////////////
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    String malStorageId;
    private int myResultCode;
    Uri contentUri;
    //////////////
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        //connect to edit_text user enters
        binding = FragmentReportMalfunctionMaintenanceManBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        device = binding.device; //phone/computer /....
        type = binding.model;
        company = binding.company;

        detailFault = binding.elsee;
        reportBut = binding.button ;
        prodSpinner = binding.products;

        malImage= binding.malImage;
        cameraBut=binding.buttonCamera;
        galleryBut=binding.buttonGallery;

        uViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        UserInt user=uViewModel.getItem().getValue();


        //show the inventory of this user in spinner
        MaintenanceController.what_insNum_show_spinner_products(prodSpinner, user.getPhone(), root);

        //if user select item
        prodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //extract the report details from the User

                ProductDetails prod = (ProductDetails) parent.getSelectedItem();
                if (prod.getDevice().equals("אחר")) {
                    device.setVisibility(View.VISIBLE);
                    type.setVisibility(View.VISIBLE);
                    company.setVisibility(View.VISIBLE);

                    reportBut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            String deviceS = device.getText().toString();
                            String typeS = type.getText().toString(); //*//
                            String companyS = company.getText().toString();
                            String detailFaultS = detailFault.getText().toString();

                            if (check_if_entered_details(deviceS, typeS, companyS, detailFaultS)) {

                                uploadImage(); //upload img to storage if exist
                                //add mal to DB
                                MaintenanceController.add_mal_and_extract_istituId(user.getPhone(), deviceS, companyS, typeS, detailFaultS ,malStorageId);

                                clear_edit_text(); //clear from edit text
                                Toast.makeText(getActivity(), "Report success", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
                else {
                    device.setVisibility(View.GONE);
                    type.setVisibility(View.GONE);
                    company.setVisibility(View.GONE);
                    reportBut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String detailFaultS = detailFault.getText().toString();

                            //check if the user entered details
                            if (check_if_entered_details(detailFaultS)) {

                                //upload img to storage if exist
                                uploadImage();
                                //add this mal to database
                                MaintenanceController.add_malfunction_with_exist_prod(prod, detailFaultS, user.getPhone() , malStorageId);

                                clear_edit_text();
                                Toast.makeText(getActivity(), "Report success", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        cameraBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "נלחץ כפתור המצלמה", Toast.LENGTH_LONG).show();
                askCameraPermissions() ;

            }
        });

        galleryBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "נלחץ כפתור הגלריה", Toast.LENGTH_LONG).show();
                SelectImage();

            }
        });


    return root;
    }

    private void uploadImage() {
        if(myResultCode == PICK_IMAGE_REQUEST ){
            malStorageId = UUID.randomUUID().toString();
            uploadImage_galery();
        }
        else if ( myResultCode == CAMERA_REQUEST_CODE){
            malStorageId = UUID.randomUUID().toString();
            uploadImageCamera(contentUri);

        }
    }
    // Select Image method
    private void SelectImage() {

        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    // UploadImage method
    private void uploadImage_galery() {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            MaintenanceController.add_gallery_to_storage(root , progressDialog ,filePath , malStorageId)
                    .addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                        // Progress Listener for loading
                     // percentage on the dialog box
                     @Override
                     public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                          double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                          progressDialog.setMessage( "Uploaded " + (int)progress + "%");
                     }
                });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_CAMERA_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
            else{
                Toast.makeText(getActivity(), "אתה צריך לאשר שימוש במצלמה", Toast.LENGTH_LONG).show();

            }
        }
    }

    ///// pic photo///////

    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(root.getContext() , Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity() , new String[]{Manifest.permission.CAMERA} ,MY_CAMERA_PERMISSION_CODE );

        }
        else {
//            openCamera();
            dispatchTakePictureIntent();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFile = "JPEG_" + timeStamp +"_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFile , ".jpg" , storageDir);
        currectPhotoPath = image.getAbsolutePath();
        return  image;

    }
    static final int REQUEST_TAKE_PHOTO = 1;
    private void dispatchTakePictureIntent() {
        System.out.println("11111111111 sstar ");

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go`
            System.out.println("11111111111 2222 ");

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                System.out.println("11111111111 hereee");

                Uri photoURI = FileProvider.getUriForFile(root.getContext(),
                        "com.example.tecknet.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);
        myResultCode = requestCode;

        if (requestCode == CAMERA_REQUEST_CODE ) {
            if(resultCode == RESULT_OK){
                File f = new File(currectPhotoPath);
                malImage.setImageURI(Uri.fromFile(f));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                getActivity().sendBroadcast(mediaScanIntent);

            }


        }
        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                malImage.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }

    }

    private void uploadImageCamera( Uri contentUri) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        MaintenanceController.add_cameraPic_to_storage(root , progressDialog ,contentUri , malStorageId)
                .addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {

            // Progress Listener for loading
            // percentage on the dialog box
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setMessage( "Uploaded " + (int)progress + "%");
            }
        });;

    }
    ///// pic photo///////

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Private function how clear the text from the edit text view
     */
    private void clear_edit_text(){
        device.getText().clear();
        type.getText().clear();
        company.getText().clear();
        detailFault.getText().clear();

    }

    private boolean check_if_entered_details(String detailFaultS){

        if(detailFaultS.isEmpty()){
            detailFault.setError("Please enter report reason!!");
            return false;
        }
        return true;

    }


    private boolean check_if_entered_details(String modelS , String typeS , String companyS, String detailFaultS){

        if(modelS.isEmpty()){
            device.setError("Please enter th device!!");
            return false;
        }
        if(typeS.isEmpty()){
            type.setError("Please enter the type!!");
            return false;
        }
        if(companyS.isEmpty()){
            company.setError("Please enter the company!!");
            return false;
        }
        if(detailFaultS.isEmpty()){
            detailFault.setError("Please enter report reason!!");
            return false;
        }
        return true;

    }


}