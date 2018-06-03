package com.laatonwalabhoot.hackernews.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.laatonwalabhoot.hackernews.R
import com.laatonwalabhoot.hackernews.common.Constants
import com.laatonwalabhoot.hackernews.data.managers.login.FirebaseLoginManager
import com.laatonwalabhoot.hackernews.ui.list.ListActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), LoginNavigator {


    private lateinit var loginViewModel: LoginViewModel

    companion object {
        fun newInstance() = LoginFragment()
    }

    /************************************
     * OVERRIDDEN METHODS
     ************************************/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        login_button.setOnClickListener {
            loginViewModel.setLoginNavigator(this)
            FirebaseLoginManager.getInstance().initIntent(activity as LoginActivity)
            startLoading()
        }
        progress.indeterminateDrawable.setColorFilter(ContextCompat
                .getColor(context!!,android.R.color.white), PorterDuff.Mode.SRC_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        /*
         * Aishwarya: handling Activity result logic in View model to remove any
         * business logic from the view
         */
        loginViewModel.handleActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        /*
         * Aishwarya: as the purpose of this app is to test code
         * hence I am forcing login on opening app.
         * Remove FirebaseLoginManager.getInstance().deleteUser(activity as LoginActivity)
         * and check user OnStart
         * to allow automatic login
         */
        FirebaseLoginManager.getInstance().signOut(activity as LoginActivity)
        FirebaseLoginManager.getInstance().deleteUser(activity as LoginActivity)
        super.onDestroy()
    }

    override fun onLoginSuccess() {
        //Login successful; Start API
        startActivity(Intent(activity, ListActivity::class.java))
    }

    override fun onLoginError(error: String) {
        //Login produced error so show dialog
        MaterialStyledDialog.Builder(context)
                .setTitle(Constants.ERROR)
                .setCancelable(true)
                .setDescription(error)
                .setHeaderScaleType(ImageView.ScaleType.CENTER)
                .setHeaderDrawable(ContextCompat.getDrawable(context!!,R.drawable.bg_error)!!)
                .withDialogAnimation(true)
                .setNeutralText(Constants.DISMISS)
                .onNeutral { dialog, _ -> dialog.dismiss()}
                .show()
        stopLoading()
    }

    override fun onLoginCancel() {
        //Login was cancelled by User
        stopLoading()
    }

    /************************************
     * PRIVATE METHODS
     ************************************/
    private fun startLoading() {
        login_layout.visibility = View.GONE
        progress_layout.visibility = View.VISIBLE
    }

    private fun stopLoading() {
        progress_layout.visibility = View.GONE
        login_layout.visibility = View.VISIBLE
    }
}
