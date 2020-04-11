package com.jm.core.common.tools.base;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jm.core.common.widget.toast.MyToast;

/**
 * 输入框的工具类
 *
 * @author jinXiong.Xie
 */

public class EditUtil {

    private EditUtil() {
    }

    /**
     * 获取edit集的字符串，若为null则弹出提示并返回为null，若不为null则返回全部输入的字符
     *
     * @param context
     * @param editTexts
     * @return
     */
    public static String[] getEditsStringAutoTip(final Context context, final EditText... editTexts) {
        return getEditsStringTip(new OnEditHintCallBack() {
            @Override
            public void onHintTip(String strHint) {
                if (!TextUtils.isEmpty(strHint)) {
                    MyToast.showToast(context, strHint);

                }
            }
        }, editTexts);
    }

    /**
     * 获取edit集的字符串
     *
     * @param callBack
     * @param editTexts
     * @return
     */
    public static String[] getEditsStringTip(OnEditHintCallBack callBack, EditText... editTexts) {
        if (NullUtil.checkNull((Object[]) editTexts)) {
            return null;
        }
        String[] strings = new String[editTexts.length];
        for (int index = 0; index < editTexts.length; index++) {
            if (TextUtils.isEmpty(getEditString(editTexts[index]))) {
                if (callBack != null) {
                    if (TextUtils.isEmpty(editTexts[index].getHint())) {
                        callBack.onHintTip("");
                    } else {
                        callBack.onHintTip(editTexts[index].getHint().toString().trim());
                    }

                }
                return null;
            }
            strings[index] = getEditString(editTexts[index]);
        }

        return strings;
    }


    /**
     * 判断两个Edit里面的字符是否相同
     *
     * @param editText1
     * @param editText2
     * @return
     */
    public static boolean checkSameEdit(EditText editText1, EditText editText2) {
        if (NullUtil.checkNull(editText1, editText2)) {
            return false;
        }
        if (editText1.getText().toString().equals(editText2.getText().toString())) {
            return true;
        }
        return false;
    }


    /**
     * 设置输入框的文字
     *
     * @param editText
     * @param strMsg
     */
    public static void setText(EditText editText, String strMsg) {

        if (editText == null) {
            return;
        }
        editText.setText(strMsg);
        if (!TextUtils.isEmpty(strMsg)) {
            editText.setSelection(strMsg.length());
        }
    }

    /**
     * 设置输入框的文字
     *
     * @param editText
     * @param strMsg
     * @param maxLength 输入框最大的容纳数
     */
    public static void setText(EditText editText, String strMsg, int maxLength) {

        if (strMsg.length() > maxLength) {
            strMsg = strMsg.substring(0, maxLength);
        }
        setText(editText, strMsg);
    }

    /**
     * 判断两次密码是否相同
     *
     * @param context
     * @param editPsw1
     * @param editPsw2
     * @return
     */
    public static boolean checkSamePsw(Context context, EditText editPsw1, EditText editPsw2) {

        if (!checkSameEdit(editPsw1, editPsw2)) {
            MyToast.showToast(context, "两次输入的密码不相同");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断两次密码是否相同(密码不能短于minLength个)
     *
     * @param context
     * @param editPsw1
     * @param editPsw2
     * @return
     */
    public static boolean checkSamePsw(Context context, EditText editPsw1, EditText editPsw2, int minLength) {

        if (!checkSameEdit(editPsw1, editPsw2)) {
            MyToast.showToast(context, "两次输入的密码不相同");
            return false;
        } else {
            String strPsw1 = editPsw1.getText().toString();
            String strPsw2 = editPsw2.getText().toString();
            if (strPsw1.length() < minLength || strPsw2.length() < minLength) {
                MyToast.showToast(context, "密码位数不能少于" + minLength + "位");
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 判断两次密码是否相同(密码不能短于minLength个)，并且判断旧密码长度是否符合
     *
     * @param context
     * @param editPswOld
     * @param editPsw1
     * @param editPsw2
     * @return
     */
    public static boolean checkSamePsw(Context context, EditText editPswOld, EditText editPsw1, EditText editPsw2, int minLength) {
        String strPsw1 = editPsw1.getText().toString();
        String strPsw2 = editPsw2.getText().toString();
        String strPswBefore = editPswOld.getText().toString();
        if (strPsw1.length() < minLength || strPsw2.length() < minLength || strPswBefore.length() < minLength) {
            MyToast.showToast(context, "密码位数不能少于" + minLength + "位");
            return false;
        }
        if (!checkSameEdit(editPsw1, editPsw2)) {
            MyToast.showToast(context, "两次输入的新密码不相同");
            return false;
        }
        return true;
    }

    /**
     * 设置最大的输入数
     *
     * @param editText
     * @param textView
     * @param maxLength
     */
    public static void setMaxLength(final EditText editText, final TextView textView, final int maxLength) {
        setMaxLength(editText, textView, maxLength, null);
    }

    /**
     * 设置最大的输入数
     *
     * @param editText
     * @param textView
     * @param maxLength
     * @param onEditTextChangedCallBack 文本修改回调
     */
    public static void setMaxLength(final EditText editText, final TextView textView, final int maxLength, final OnEditTextChangedCallBack onEditTextChangedCallBack) {
        if (editText != null) {
            //限制字数
            InputFilter[] filters = {new InputFilter.LengthFilter(maxLength)};
            editText.setFilters(filters);
            //监听
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (onEditTextChangedCallBack != null) {
                        onEditTextChangedCallBack.beforeTextChanged(charSequence, i, i1, i2);
                    }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (onEditTextChangedCallBack != null) {
                        onEditTextChangedCallBack.onTextChanged(charSequence, i, i1, i2);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (textView != null) {
                        textView.setText("您还可以输入" + (maxLength - editText.getText().length()) + "个字");
                    }
                    if (onEditTextChangedCallBack != null) {
                        onEditTextChangedCallBack.afterTextChanged(editable);
                    }
                }
            });
        }
    }

    /**
     * 获取EditText中的数据
     *
     * @param editText
     * @return
     */
    public static String getEditString(EditText editText) {
        if (editText == null) {
            return "";
        }
        return editText.getText().toString().trim();
    }

    /**
     * 小数的位数
     */
    private static final int DECIMAL_DIGITS = 2;

    public static void setMoneyEditType(final EditText editText) {
        setMoneyEditType(editText, DECIMAL_DIGITS);
    }

    /**
     * 设置金额类型的EditText
     * 功能：
     * 1、.开头自动变为0.
     * 2、.结尾不能再输入.
     * 3、可设置最多允许输入的小数位数
     *
     * @param editText
     * @param pointNum 最多允许多少位小数
     */
    public static void setMoneyEditType(final EditText editText, final int pointNum) {
        setMoneyEditType(editText, pointNum, null);
    }

    /**
     * 设置金额类型的EditText
     * 功能：
     * 1、.开头自动变为0.
     * 2、.结尾不能再输入.
     * 3、可设置最多允许输入的小数位数
     *
     * @param editText
     * @param pointNum                  最多允许多少位小数
     * @param onEditTextChangedCallBack 金额类型的EditText文本改变时的回调
     */
    public static void setMoneyEditType(final EditText editText, final int pointNum, final OnEditTextChangedCallBack onEditTextChangedCallBack) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().startsWith(".")) {
                    editText.setText("0" + editText.getText().toString().trim());
                }
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > pointNum) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + pointNum + 1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }

                if (onEditTextChangedCallBack != null) {
                    onEditTextChangedCallBack.onTextChanged(s, start, before, count);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (onEditTextChangedCallBack != null) {
                    onEditTextChangedCallBack.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (onEditTextChangedCallBack != null) {
                    onEditTextChangedCallBack.afterTextChanged(s);
                }
            }
        });
    }

    /**
     * 用于EditText文本改变时的回调
     */
    public interface OnEditTextChangedCallBack {
        void onTextChanged(CharSequence s, int start, int before, int count);

        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void afterTextChanged(Editable s);
    }

    /**
     * edit的Hint提示
     */

    public interface OnEditHintCallBack {

        void onHintTip(String strHint);
    }


    /**
     * 标识搜索延迟
     */
    private static final int SEARCH_DELAYED = 1;
    /**
     * 输入时间间隔
     */
    private static final int INTERVAL = 500;

    private static SearchDelayedCallBack searchDelayedCallBack;

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEARCH_DELAYED:
                    if (searchDelayedCallBack != null && msg.obj != null) {
                        searchDelayedCallBack.searchDelayed(msg.obj.toString());
                    }
                    break;
                default:
            }
        }
    };

    /**
     * 延迟搜索
     *
     * @param editText
     * @param searchDelayedCallBack
     */
    public static final void searchDelayed(EditText editText, final SearchDelayedCallBack searchDelayedCallBack) {
        searchDelayed(editText, searchDelayedCallBack, null);
    }


    /**
     * 延迟搜索
     *
     * @param editText
     * @param searchDelayedCallBack
     */
    public static final void searchDelayed(EditText editText, final SearchDelayedCallBack searchDelayedCallBack, final OnEditTextChangedCallBack onEditTextChangedCallBack) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (onEditTextChangedCallBack != null) {
                    onEditTextChangedCallBack.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (onEditTextChangedCallBack != null) {
                    onEditTextChangedCallBack.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (onEditTextChangedCallBack != null) {
                    onEditTextChangedCallBack.afterTextChanged(s);
                }

                if (mHandler.hasMessages(SEARCH_DELAYED)) {
                    mHandler.removeMessages(SEARCH_DELAYED);
                }

                EditUtil.searchDelayedCallBack = searchDelayedCallBack;
                Message message = new Message();
                message.what = SEARCH_DELAYED;
                if (s != null) {

                    message.obj = s.toString();
                }
                mHandler.sendMessageDelayed(message, INTERVAL);
            }
        });
    }

    /**
     * 延迟搜索
     */
    public interface SearchDelayedCallBack {
        void searchDelayed(String s);
    }


    /**
     * 设置EditText的状态监听
     *
     * @param editStatusCallBack
     * @param editTexts
     */
    public static final void setEditStatusListener(final EditStatusCallBack editStatusCallBack, final EditText... editTexts) {

        if (editStatusCallBack == null) {
            return;
        }


        for (final EditText editText : editTexts) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {


                    checkEditStatus(editStatusCallBack, editTexts);
                }
            });
        }

        checkEditStatus(editStatusCallBack, editTexts);

    }

    /**
     * 判断Edit的状态
     *
     * @param editStatusCallBack
     * @param editTexts
     */
    private static void checkEditStatus(EditStatusCallBack editStatusCallBack, final EditText... editTexts) {
        EditText nullEdit = checkEditHaveNullString(editTexts);
        boolean allFill = nullEdit == null;
        if (allFill) {
            editStatusCallBack.allFill();
        } else {
            editStatusCallBack.haveNull(nullEdit);
        }
    }


    /**
     * 判断输入框中是否包含空的字符
     *
     * @param editTexts
     * @return 若含有空的字符则返回该输入框，若未null，则全部不为null
     */
    public static EditText checkEditHaveNullString(EditText... editTexts) {
        //存储内部为null的EditText
        EditText nullEdit = null;

        for (EditText text : editTexts) {
            if (TextUtils.isEmpty(text.getText().toString().trim())) {
                nullEdit = text;
                break;
            }
        }

        return nullEdit;
    }


    /**
     * 输入框状态回调（含有null和全不为null）
     */
    public interface EditStatusCallBack {
        /**
         * 含有null值
         *
         * @param editText 最开始检查到null的EditText
         */
        void haveNull(EditText editText);

        /**
         * 全不为null
         */
        void allFill();
    }

    /**
     * 设置搜索监听
     *
     * @param editText
     * @param searchListener
     */
    public static void setSearchListener(final EditText editText, final SearchListener searchListener) {
        editText.setSingleLine(true);
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchListener != null) {
                        searchListener.search(getEditString(editText));
                    }
                }
                return false;
            }
        });
    }


    /**
     * 搜索
     */
    public interface SearchListener {
        /**
         * 搜索
         *
         * @param value Edit里面的值
         */
        void search(String value);
    }

    /**
     * 设置搜索监听（假如editText的内容为null时，调用搜索方法）
     *
     * @param editText
     * @param searchListener
     */
    public static void setSearchAndNullListener(final EditText editText, final SearchListener searchListener) {
        setSearchListener(editText, searchListener);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    if (searchListener != null) {
                        searchListener.search("");
                    }
                }
            }
        });
    }

    /**
     * EditText和View进行联动，若EditView值为null，则显示，若不为空，则隐藏
     *
     * @param editText
     * @param view
     */
    public static void linkageEditWithView(EditText editText, final View view) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


}
