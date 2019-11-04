package com.tarasapp.modulapp.restaurant.fragments


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.squareup.picasso.Picasso
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.activity.EditUserActivity
import com.tarasapp.modulapp.restaurant.activity.idUser
import com.tarasapp.modulapp.restaurant.models.User
import com.tarasapp.modulapp.restaurant.presenters.PersonalAreaFragmentPresenter
import com.tarasapp.modulapp.restaurant.views.PersonalAreaFragmentView
import kotlinx.android.synthetic.main.fragment_personal_area.*
import java.io.File


class PersonalAreaFragment : MvpAppCompatFragment(), PersonalAreaFragmentView {

    @InjectPresenter
    lateinit var personalAreaFragmentPresenter: PersonalAreaFragmentPresenter

    private lateinit var userId: String

    override fun showUser(user: User) {
        val file = File(user.avatarUrl)
        Picasso.with(activity).load(file).error(R.drawable.ic_image_black_24dp).into(_image_id)
        email_user_text.text = user.email
        phone_user_text.text = user.phone
        val str = user.fname + " " + user.lname
        name_text.text = str
        city_text.text = user.city
        userId = user.id
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_personal_area, container, false)
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.user_toolbar)
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_edit -> {
                val intent = Intent(context, EditUserActivity::class.java)
                val bundle = Bundle()
                bundle.putString(idUser, userId)
                intent.putExtras(bundle)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
