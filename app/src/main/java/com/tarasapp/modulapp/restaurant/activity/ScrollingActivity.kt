package com.tarasapp.modulapp.restaurant.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.android.material.appbar.AppBarLayout
import com.tarasapp.modulapp.restaurant.R
import com.tarasapp.modulapp.restaurant.fragments.PhotoFragment
import com.tarasapp.modulapp.restaurant.fragments.photo1
import com.tarasapp.modulapp.restaurant.fragments.photo2
import com.tarasapp.modulapp.restaurant.fragments.photo3
import com.tarasapp.modulapp.restaurant.models.Dish
import com.tarasapp.modulapp.restaurant.presenters.DishDetailsPresenter
import com.tarasapp.modulapp.restaurant.views.DishDetailsView
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*


const val nameOfDish = "nameOfDish"
const val keyOfDish = "keyOfDish"

class ScrollingActivity : MvpAppCompatActivity(), DishDetailsView {
    override fun showMessage(message: String) {
        Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
    }

    @InjectPresenter
    lateinit var dishDetailsPresenter: DishDetailsPresenter

    lateinit var key: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbarExp)
        toolbarExp.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar_layout.isTitleEnabled = true
//        val dish =intent.extras.getString(nameOfDish)
        key = intent.extras.getString(keyOfDish)
        dishDetailsPresenter.showDishFromDatabase(key)


        app_bar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var isFbaShow = true
            var scrollRange = -1
            override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
                if (scrollRange == -1) {
                    scrollRange = app_bar.totalScrollRange
                }
                if (scrollRange + p1 == 0) {
                    isShow = true
                }
                else if (isShow) {
                    toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)
                    isShow = false
                }
                if(scrollRange + p1 <= 100){
                    fab_menu.hideMenu(false)
                    isFbaShow = false
                } else if(!isFbaShow){
                    fab_menu.showMenu(true)
                }
            }

        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        finish()
    }

    override fun showDish(dish: Dish) {
        toolbar_layout.isTitleEnabled = false
        toolbarExp.title=""
        fb_action1.setOnClickListener {
            dishDetailsPresenter.addItemToBase(keyOfDish = key)
            fab_menu.close(true)
        }
        fb_action2.setOnClickListener {
            Toast.makeText(applicationContext,"2",Toast.LENGTH_LONG).show()
            fab_menu.close(true)
        }
        dish_title_tv.text = dish.names
        dish_description_tv.text = dish.description
        val fragment = PhotoFragment()
        val bundle = Bundle()
        bundle.putString(photo1, dish.imageUrl[photo1])
        bundle.putString(photo2, dish.imageUrl[photo2])
        bundle.putString(photo3, dish.imageUrl[photo3])
        fragment.arguments = bundle
        val fmTransaction = supportFragmentManager.beginTransaction()
        fmTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
        fmTransaction.add(R.id.img_container, fragment).addToBackStack(null).commit()
    }

}
