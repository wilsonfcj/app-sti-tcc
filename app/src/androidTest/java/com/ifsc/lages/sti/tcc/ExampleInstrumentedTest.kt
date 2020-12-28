package com.ifsc.lages.sti.tcc

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ifsc.lages.sti.tcc.resources.generics.BaseResponse
import com.ifsc.lages.sti.tcc.resources.result.ResultadoResponse
import com.ifsc.lages.sti.tcc.resources.result.ResultadoService

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun resultadoGeral() {
        // Context of the app under test.
        var resultadoService = ResultadoService()
        var result : BaseResponse<ResultadoResponse.GeralUsuario> = resultadoService.loadOverallResult(4);
        assert(result.success!!)
    }
}
