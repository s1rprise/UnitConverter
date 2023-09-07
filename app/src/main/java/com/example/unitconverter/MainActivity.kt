package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainAppFunction()
                }
            }
        }
    }
}

@Composable
fun MainAppFunction() {
    var value by remember {
        mutableStateOf("")
    }
    var isChecked by remember {
        mutableStateOf(false)
    }
    val valueConverted = value.toDoubleOrNull() ?: 0.0
    val unitConverter = unitConvert(valueConverted, isChecked)
    Column(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_app)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(alignment = Alignment.Start)
        )
        EditNumberField(
            label = R.string.label,
            icon = R.drawable.rule,
            value = value,
            onValueChanged = { value = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.padding_small))
        )
        SwitchActivate(
            checked = isChecked,
            onCheckedChanged = { isChecked = it }
        )
        Text(
            stringResource(R.string.result_convert, unitConverter),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .padding(vertical = dimensionResource(R.dimen.padding_small))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes icon: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        label = { Text(stringResource(label)) },
        onValueChange = onValueChanged,
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        leadingIcon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = null
            )
        }
    )
}

@Composable
fun SwitchActivate(
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(dimensionResource(R.dimen.switch_size)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(R.string.round_up),
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChanged,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

private fun unitConvert(value: Double, roundUp: Boolean): String {
    var convert = value / 1000
    if (roundUp)
        convert = kotlin.math.round(convert)
    return String.format("%.2f", convert)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UnitConverterTheme {
        MainAppFunction()
    }
}