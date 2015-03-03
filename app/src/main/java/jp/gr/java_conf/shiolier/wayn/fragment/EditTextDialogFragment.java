package jp.gr.java_conf.shiolier.wayn.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

public class EditTextDialogFragment extends DialogFragment {
	private DialogInterface.OnClickListener okClickListener = null;
	private DialogInterface.OnClickListener cancelClickListener = null;
	private EditText editText;

	public static EditTextDialogFragment newInstance(String title, String message) {
		EditTextDialogFragment fragment = new EditTextDialogFragment();
		Bundle args = new Bundle();
		args.putString("title", title);
		args.putString("message", message);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString("title");
		String message = getArguments().getString("message");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(title)
				.setMessage(message)
				.setView(this.editText)
				.setPositiveButton("OK", this.okClickListener);

		setCancelable(false);

		return builder.create();
	}

	/**
	 * OKクリックリスナーの登録
	 */
	public void setOnOkClickListener(DialogInterface.OnClickListener listener) {
		this.okClickListener = listener;
	}

	/**
	 * Cancelクリックリスナーの登録
	 */
	public void setOnCancelClickListener(DialogInterface.OnClickListener listener) {
		this.cancelClickListener = listener;
	}

	/**
	 * EditTextの登録
	 */
	public void setEditText(EditText editText) {
		this.editText = editText;
	}
}
