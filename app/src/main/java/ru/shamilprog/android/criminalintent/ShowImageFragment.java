package ru.shamilprog.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ShowImageFragment extends DialogFragment {

    public static final String EXTRA_IMAGE =
            "ru.shamilprog.android.criminalintent.image";
    private static final String ARG_IMAGE = "image";

    private ImageView mZoomedImage;

    public static ShowImageFragment newInstance(File file) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_IMAGE, file);

        ShowImageFragment fragment = new ShowImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        File file = (File) getArguments().getSerializable(ARG_IMAGE);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_image, null);

        Bitmap bitmap = PictureUtils.getScaledBitmap(
                file.getPath(), getActivity());

        mZoomedImage = (ImageView) v.findViewById(R.id.zoomed_image);
    //    mZoomedImage.setMinimumWidth();
        mZoomedImage.setImageBitmap(bitmap);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK, null);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_IMAGE, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
