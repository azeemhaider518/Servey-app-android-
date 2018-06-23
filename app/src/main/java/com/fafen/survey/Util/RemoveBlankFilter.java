package com.fafen.survey.Util;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by andresrodriguez on 6/19/18.
 */

public class RemoveBlankFilter {

    public InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String filtered = "";
            for (int i = start; i < end; i++) {
                char character = source.charAt(i);
                if (!Character.isWhitespace(character)) {
                    filtered += character;
                }
            }

            return filtered;
        }

    };
}
