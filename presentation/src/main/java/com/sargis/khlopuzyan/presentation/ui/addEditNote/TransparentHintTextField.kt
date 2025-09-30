package com.sargis.khlopuzyan.presentation.ui.addEditNote

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.sargis.khlopuzyan.presentation.util.TestTags

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    modifier: Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    testTag: String,
    onFocusChange: (FocusState) -> Unit,
) {
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .testTag(testTag)
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                }
        )

        if (isHintVisible && text.isEmpty()) {
            Text(
                text = hint,
                style = textStyle,
                color = Color.DarkGray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransparentHintTextFieldPreview() {
    TransparentHintTextField(
        text = "Text",
        hint = "Hint",
        modifier = Modifier,
        onValueChange = {},
        testTag = TestTags.TITLE_TEXT_FIELD,
        onFocusChange = {},
    )
}