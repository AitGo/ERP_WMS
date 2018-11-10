package com.rbu.erp_wms.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rbu.erp_wms.R;


public class MyEditDialog extends Dialog {


    public MyEditDialog(Context context) {
        super(context);
    }

    public MyEditDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context                         context;
        private String                          title;
        private String                          hint;
        private String                          positiveButtonText;
        private String                          negativeButtonText;
        private View                            contentView;
        private ConfirmListener                 positiveButtonClickListener;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        private TextView                        tv_title;
        private EditText                        et_input;
        private Button                          btn_confirm, btn_cancel;

        public Builder(Context context) {
            this.context = context;
        }

        //        public Builder setMessage(String message) {
        //            this.message = message;
        //            return this;
        //        }

        public Builder setEditHint(String hint) {
            this.hint = hint;
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         ConfirmListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         ConfirmListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }


        public Builder setNegativeButton(int negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        @SuppressWarnings("deprecation")
        public MyEditDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final MyEditDialog dialog = new MyEditDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.item_edit_dialog, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // set the dialog title
            et_input = (EditText) layout.findViewById(R.id.lamp_dialog_input);
            tv_title = (TextView) layout.findViewById(R.id.lamp_dialog_title);
            tv_title.setText(title);
            if (positiveButtonClickListener != null) {
                ((Button) layout.findViewById(R.id.dialog_confirm))
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                positiveButtonClickListener.onClick(et_input.getText().toString());
                            }
                        });
            }
            if (negativeButtonClickListener != null) {
                ((Button) layout.findViewById(R.id.dialog_cancel))
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                negativeButtonClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_NEGATIVE);
                            }
                        });
            }
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setContentView(layout);
            return dialog;
        }

    }

    public interface ConfirmListener {
        void onClick(String input);
    }

}
