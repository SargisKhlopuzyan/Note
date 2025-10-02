package com.sargis.khlopuzyan.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sargis.khlopuzyan.domain.util.NoteOrder
import com.sargis.khlopuzyan.domain.util.OrderType
import com.sargis.khlopuzyan.presentation.util.TestTags

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit,
) {
    Column(
        modifier = modifier.semantics {
            this.testTagsAsResourceId = true
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    this.testTagsAsResourceId = true
                },
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrder is NoteOrder.Title,
                tag = TestTags.ORDER_SECTION_TITLE,
                onSelect = {
                    onOrderChange(
                        NoteOrder.Title(noteOrder.orderType)
                    )
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Date",
                selected = noteOrder is NoteOrder.Date,
                tag = TestTags.ORDER_SECTION_DATE,
                onSelect = {
                    onOrderChange(NoteOrder.Date(noteOrder.orderType))
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Color",
                selected = noteOrder is NoteOrder.Color,
                tag = TestTags.ORDER_SECTION_COLOR,
                onSelect = {
                    onOrderChange(NoteOrder.Color(noteOrder.orderType))
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                tag = TestTags.ORDER_SECTION_ASCENDING,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Ascending))
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                tag = TestTags.ORDER_SECTION_DESCENDING,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderSectionPreview() {
    OrderSection(onOrderChange = {})
}