package com.lupa.suitgame2.ui.dialog

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.lupa.suitgame2.databinding.ActivityDialogMenuBinding

object DialogUtils {

    fun showInputNameDialog(
        context: Context,
        title: String,
        onButtonClick: (AlertDialog?, String) -> (Unit)
    ) {
        var dialog: AlertDialog? = null
        val dialogBinding =
            ActivityDialogMenuBinding.inflate((context as AppCompatActivity).layoutInflater).apply {
                tvWinner.text = title
                btnPlayAgain.setOnClickListener {
                    onButtonClick(dialog, "play again")
                }
                btnBackToMenu.setOnClickListener {
                    onButtonClick(dialog, "back")
                }
            }
        dialog = AlertDialog.Builder(context)
            .setCancelable(false)
            .setView(dialogBinding.root)
            .create()
        dialog.show()
    }
}