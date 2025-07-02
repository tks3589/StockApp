package com.example.stockapp.app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.stockapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment: BottomSheetDialogFragment() {
    companion object {
        fun newInstance(onSorted: (Boolean) -> Unit): BottomSheetFragment {
            return BottomSheetFragment().apply {
                this.onSorted = onSorted
            }
        }
    }

    private var onSorted: ((Boolean) -> Unit)? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_sort_option, container, false)
        val sortByAsc: TextView = view.findViewById(R.id.sort_by_asc)
        val sortByDesc: TextView = view.findViewById(R.id.sort_by_desc)
        sortByAsc.setOnClickListener {
            onSorted?.invoke(true)
            dismiss()
        }
        sortByDesc.setOnClickListener {
            onSorted?.invoke(false)
            dismiss()
        }
        return view
    }
}