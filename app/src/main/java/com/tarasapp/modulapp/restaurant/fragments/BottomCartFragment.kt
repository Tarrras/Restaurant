package com.tarasapp.modulapp.restaurant.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tarasapp.modulapp.restaurant.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_bottom_cart.*


class BottomCartFragment : BottomSheetDialogFragment() {

    private lateinit var listener: BottomSheetListener
    private var myCount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_cart, container, false)
    }

    override fun onResume() {
        super.onResume()
        myCount = arguments?.getInt("count")!!
        count_edit_tv.text = myCount.toString()
        button.setOnClickListener {
            arguments?.getString("key")?.let { it1 ->
                listener.changeCount(
                    count_edit_tv.text.toString().toInt(),
                    it1,
                    arguments!!.getInt("position")
                )
            }
            dismiss()
        }

        Observable.create<Int> { emitter ->
            action_minus_bt.setOnClickListener {
                emitter.onNext(--myCount)
            }
        }.mergeWith(Observable.create<Int> { emitter ->
                action_plus_bt.setOnClickListener {
                    emitter.onNext(++myCount)
                }
            }).subscribe { s -> count_edit_tv.text = s.toString() }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as BottomSheetListener
    }

}

interface BottomSheetListener {
    fun changeCount(count: Int, key: String, position: Int)
}
