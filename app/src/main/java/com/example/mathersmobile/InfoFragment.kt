package com.example.mathersmobile

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_info.view.*

class InfoFragment : Fragment() {
    private var listener: InfoFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        view.backFromInfo.setOnClickListener {
            listener?.backFromInfoOnClick()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is InfoFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface InfoFragmentListener {
        fun backFromInfoOnClick()
    }

}
