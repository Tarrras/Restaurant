package com.tarasapp.modulapp.restaurant.fragments


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.Picasso
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.activity.EditUserActivity
import com.tarasapp.modulapp.restaurant.activity.SignInActivity
import com.tarasapp.modulapp.restaurant.activity.idUser
import com.tarasapp.modulapp.restaurant.models.User
import com.tarasapp.modulapp.restaurant.presenters.PersonalAreaFragmentPresenter
import com.tarasapp.modulapp.restaurant.views.PersonalAreaFragmentView
import kotlinx.android.synthetic.main.persinal_account.*
import java.io.File


class PersonalAreaFragment : MvpAppCompatFragment(), PersonalAreaFragmentView {

    @InjectPresenter
    lateinit var personalAreaFragmentPresenter: PersonalAreaFragmentPresenter

    private lateinit var userId: String

    override fun showUser(user: User) {
        fb_edit.setOnClickListener {
            val intent = Intent(context, EditUserActivity::class.java)
            val bundle = Bundle()
            bundle.putString(idUser, userId)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        fb_logout.setOnClickListener {
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
        }
        val file = File(user.avatarUrl)
        Picasso.with(activity).load(file).error(R.drawable.ic_image_black_24dp).into(user_image)
        user_email.text = user.email
        user_phone.text = user.phone
        val str = user.fname + " " + user.lname
        user_name.text = str
        user_location.text = user.city
        userId = user.id

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.persinal_account, container, false)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_user)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        personalAreaFragmentPresenter.loadUser()
        app_bar_user.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var isFbaShow = true
            var scrollRange = -1
            override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
                if (scrollRange == -1) {
                    scrollRange = app_bar_user.totalScrollRange
                }
                if (scrollRange + p1 == 0) {
                    isShow = true
                }
                else if (isShow) {
                    toolbar_layout_user.setCollapsedTitleTextColor(Color.BLACK)
                    isShow = false
                }
                if(scrollRange + p1 <= 110){
                    fab_menu_user.hideMenu(false)
                    isFbaShow = false
                } else if(!isFbaShow){
                    fab_menu_user.showMenu(true)
                }
            }

        })
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.user_menu,menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when(item.itemId){
//            R.id.action_edit -> {
//                val intent = Intent(context, EditUserActivity::class.java)
//                val bundle = Bundle()
//                bundle.putString(idUser, userId)
//                intent.putExtras(bundle)
//                startActivity(intent)
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }


}
