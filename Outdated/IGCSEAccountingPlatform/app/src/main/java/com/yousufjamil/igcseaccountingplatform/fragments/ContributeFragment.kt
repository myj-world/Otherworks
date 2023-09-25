package com.yousufjamil.igcseaccountingplatform.fragments

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yousufjamil.igcseaccountingplatform.R
import jp.wasabeef.richeditor.RichEditor

class ContributeFragment: Fragment(R.layout.contribute_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val mEditor = getView()?.findViewById<RichEditor>(R.id.editorContribute)

        fun setPopup(type: String, data1: Any, data2: Any?, data3: Any?) {
            when (type) {
                "FontSize" -> {
                    val datamodified = data1.toString().toInt()
                    mEditor?.setFontSize(datamodified)
                }
                "AddLink" -> {
                    println("$data1 ${data2.toString()}")
                    mEditor?.insertLink(data2.toString(), data1.toString())
                }
                "TextBg" -> {
                    try {
                        mEditor?.setTextBackgroundColor(Color.parseColor(data1.toString()))
                    }
                    catch (_: Throwable) {
                        Toast.makeText(context, "Unknown Color", Toast.LENGTH_SHORT).show()
                    }
                }
                "TextColor" -> {
                    try {
                        mEditor?.setTextColor(Color.parseColor(data1.toString()))
                    }
                    catch (_: Throwable) {
                        Toast.makeText(context, "Unknown Color", Toast.LENGTH_SHORT).show()
                    }
                }
                "InsertImage" -> {
                    mEditor?.insertImage(data1.toString(), data2.toString(), data3.toString().toInt())
                }
                else -> Toast.makeText(context, "Unknown Error", Toast.LENGTH_SHORT).show()
            }
        }

        fun showPopup(type: String) {
            var data: Any = ""
            var data1: Any = ""
            var data2: Any = ""
            var data3: Any = ""

            val builder = AlertDialog.Builder(context)

            val inflator = layoutInflater

            val inputLayout = inflator.inflate(R.layout.popup_1et, null)
            val input = inputLayout.findViewById<EditText>(R.id.editTextText)

            val inputLayout2 = inflator.inflate(R.layout.popup_2et, null)
            val input1 = inputLayout2.findViewById<EditText>(R.id.editTextText_2layout)
            val input2 = inputLayout2.findViewById<EditText>(R.id.editTextText_2layout_2)

            val inputLayout3 = inflator.inflate(R.layout.popup_3et, null)
            val input3 = inputLayout3.findViewById<EditText>(R.id.editTextText_3layout)
            val input4 = inputLayout3.findViewById<EditText>(R.id.editTextText_3layout_2)
            val input5 = inputLayout3.findViewById<EditText>(R.id.editTextText_3layout_3)

            val addExtraStart = inputLayout.findViewById<TextView>(R.id.addExtraStart)
            val addExtraEnd = inputLayout.findViewById<TextView>(R.id.addExtraEnd)
            val addExtraStart2 = inputLayout2.findViewById<TextView>(R.id.addExtraStart2)
            val addExtraEnd2 = inputLayout2.findViewById<TextView>(R.id.addExtraEnd2)

            when (type) {
                "FontSize" -> {
                    builder.setTitle("Set Font Size")
                    input.inputType = InputType.TYPE_CLASS_NUMBER
                    input.hint = "Enter a font size from 1 to 7..."
                    input.maxLines = 1
                    builder.setView(inputLayout)
                }
                "AddLink" -> {
                    builder.setTitle("Enter link")
                    input1.inputType = InputType.TYPE_CLASS_TEXT
                    input1.hint = "Enter text to display..."
                    input2.inputType = InputType.TYPE_CLASS_TEXT
                    input2.hint = "Enter the link..."
                    builder.setView(inputLayout2)
                }
                "TextBg" -> {
                    builder.setTitle("Enter text highlight color in HEX")
                    input.inputType = InputType.TYPE_CLASS_TEXT
                    input.hint = "Enter color in 6 character HEX"
                    input.maxLines = 1
                    addExtraStart.text = "#"
                    builder.setView(inputLayout)
                }

                "TextColor" -> {
                    builder.setTitle("Enter text color in HEX")
                    input.inputType = InputType.TYPE_CLASS_TEXT
                    input.hint = "Enter color in 6 character HEX"
                    input.maxLines = 1
                    addExtraStart.text = "#"
                    builder.setView(inputLayout)
                }

                "InsertImage" -> {
                    builder.setTitle("Insert Image")
                    input3.inputType = InputType.TYPE_CLASS_TEXT
                    input3.hint = "Insert Image URL..."
                    input4.inputType = InputType.TYPE_CLASS_TEXT
                    input4.hint = "Enter alternative text..."
                    input5.inputType = InputType.TYPE_CLASS_NUMBER
                    input5.hint = "Enter image width between 10 and 320..."
                    builder.setView(inputLayout3)
                }
                else -> Toast.makeText(context, "Unknown Error", Toast.LENGTH_SHORT).show()
            }

            builder.setPositiveButton("OK") { dialog, which ->
                when (type) {
                    "FontSize" -> {
                        data = input.text.toString()
                        if (data != "" && data.toString().toInt() >= 0 && data.toString().toInt() <= 7) {
                            setPopup(type, data, null, null)
                        } else if (data.toString().toInt() < 0 || data.toString().toInt() > 7) {
                            Toast.makeText(context, "Font size should be a value from 1 and 7 only", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Invalid value entered", Toast.LENGTH_SHORT).show()
                        }
                    }
                    "AddLink" -> {
                        data1 = input1.text.toString()
//                    data2 = "${input2.text.toString()}.com"
                        data2 = input2.text.toString()
                        if (data2.toString().contains(".")) {
                            setPopup(type, data1, data2, null)
                        } else {
                            Toast.makeText(context, "Invalid URL entered", Toast.LENGTH_SHORT).show()
                        }
                    }
                    "TextBg" -> {
                        data = "#${input.text.toString()}"
                        if (input.text.length == 6) {
                            setPopup(type, data, null, null)
                        } else {
                            Toast.makeText(context, "Ensure HEX is 6 characters long", Toast.LENGTH_SHORT).show()
                        }
                    }
                    "TextColor" -> {
                        data = "#${input.text.toString()}"
                        if (input.text.length == 6) {
                            setPopup(type, data, null, null)
                        } else {
                            Toast.makeText(context, "Ensure HEX is 6 characters long", Toast.LENGTH_SHORT).show()
                        }
                    }

                    "InsertImage" -> {
                        data1 = input3.text.toString()
                        data2 = input4.text.toString()
                        data3 = input5.text.toString()
                        if (data1.toString().contains(".") && data3.toString().toInt() <=320 && data3.toString().toInt() >=10) {
                            setPopup(type, data1, data2, data3)
                        } else if (data3.toString().toInt() >=320 || data3.toString().toInt() <=10) {
                            Toast.makeText(context, "Invalid width entered", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Invalid URL entered", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else -> Toast.makeText(context, "Unknown Error", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            builder.show()
        }

        mEditor?.setEditorHeight(100)
        mEditor?.setEditorFontSize(18)
        mEditor?.setPadding(10, 10, 10, 10)
        mEditor?.setPlaceholder("Type your blog here...")
//        mEditor?.background = ColorDrawable(R.attr.light)

        var htmlText = ""

        mEditor?.setOnTextChangeListener {text ->
            htmlText = text
        }

        getView()?.findViewById<ImageButton>(R.id.action_undoContribute)?.setOnClickListener { mEditor?.undo() }

        getView()?.findViewById<View>(R.id.action_redoContribute)?.setOnClickListener { mEditor?.redo() }

        getView()?.findViewById<View>(R.id.action_boldContribute)?.setOnClickListener { mEditor?.setBold() }

        getView()?.findViewById<View>(R.id.action_italicContribute)?.setOnClickListener { mEditor?.setItalic() }

        getView()?.findViewById<View>(R.id.action_subscriptContribute)?.setOnClickListener { mEditor?.setSubscript() }

        getView()?.findViewById<View>(R.id.action_superscriptContribute)?.setOnClickListener { mEditor?.setSuperscript() }

        getView()?.findViewById<View>(R.id.action_strikethroughContribute)?.setOnClickListener { mEditor?.setStrikeThrough() }

        getView()?.findViewById<View>(R.id.action_underlineContribute)?.setOnClickListener { mEditor?.setUnderline() }

        getView()?.findViewById<View>(R.id.action_heading1Contribute)?.setOnClickListener { mEditor?.setHeading(1) }

        getView()?.findViewById<View>(R.id.action_heading2Contribute)?.setOnClickListener { mEditor?.setHeading(2) }

        getView()?.findViewById<View>(R.id.action_heading3Contribute)?.setOnClickListener { mEditor?.setHeading(3) }

        getView()?.findViewById<View>(R.id.action_heading4Contribute)?.setOnClickListener { mEditor?.setHeading(4) }

        getView()?.findViewById<View>(R.id.action_heading5Contribute)?.setOnClickListener { mEditor?.setHeading(5) }

        getView()?.findViewById<View>(R.id.action_heading6Contribute)?.setOnClickListener { mEditor?.setHeading(6) }

        getView()?.findViewById<View>(R.id.action_txt_colorContribute)?.setOnClickListener {
                showPopup("TextColor")
            }

        getView()?.findViewById<View>(R.id.action_bg_colorContribute)?.setOnClickListener {
                showPopup("TextBg")
            }

        getView()?.findViewById<ImageButton>(R.id.action_indentContribute)?.setOnClickListener { mEditor?.setIndent() }

        getView()?.findViewById<View>(R.id.action_outdentContribute)?.setOnClickListener { mEditor?.setOutdent() }

        getView()?.findViewById<View>(R.id.action_align_leftContribute)?.setOnClickListener { mEditor?.setAlignLeft() }

        getView()?.findViewById<View>(R.id.action_align_centerContribute)?.setOnClickListener { mEditor?.setAlignCenter() }

        getView()?.findViewById<View>(R.id.action_align_rightContribute)?.setOnClickListener { mEditor?.setAlignRight() }

        getView()?.findViewById<View>(R.id.action_blockquoteContribute)?.setOnClickListener { mEditor?.setBlockquote() }

        getView()?.findViewById<View>(R.id.action_insert_bulletsContribute)?.setOnClickListener { mEditor?.setBullets() }

        getView()?.findViewById<View>(R.id.action_insert_numbersContribute)?.setOnClickListener { mEditor?.setNumbers() }

        getView()?.findViewById<View>(R.id.action_insert_imageContribute)?.setOnClickListener {
            showPopup("InsertImage")
        }

        getView()?.findViewById<View>(R.id.action_insert_linkContribute)?.setOnClickListener {
            showPopup("AddLink")
        }

        getView()?.findViewById<View>(R.id.action_insert_checkboxContribute)?.setOnClickListener { mEditor?.insertTodo() }

        getView()?.findViewById<View>(R.id.action_change_font_sizeContribute)?.setOnClickListener {
            showPopup("FontSize")
        }

        getView()?.findViewById<Button>(R.id.postContribute)?.setOnClickListener {
            val title = getView()?.findViewById<EditText>(R.id.titleEtContribute)?.text

            if (title.toString() != "" && htmlText != "") {
                Toast.makeText(context, "Current Title: $title ,Current HTML: $htmlText", Toast.LENGTH_SHORT).show()
            } else if (title.toString() == "") {
                Toast.makeText(context, "Please enter a title", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Blog cannot be posted without any content", Toast.LENGTH_SHORT).show()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}