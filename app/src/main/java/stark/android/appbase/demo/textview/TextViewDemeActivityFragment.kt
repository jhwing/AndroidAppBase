package stark.android.appbase.demo.textview

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_textview_demo.*
import stark.android.appbase.demo.R
import stark.android.appbase.fragment.BaseFragment

class TextViewDemeActivityFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_textview_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addBtn.setOnClickListener {
            numberTextView.text = "2" + numberTextView.text
        }

        delBtn.setOnClickListener {
            val text = numberTextView.text.toString()
            if (TextUtils.isEmpty(text)) {
                numberTextView.text = ""
            } else {
                numberTextView.text = text.substring(0, text.length - 1)
            }
        }

        clearBtn.setOnClickListener {
            numberTextView.text = ""
        }
    }

}