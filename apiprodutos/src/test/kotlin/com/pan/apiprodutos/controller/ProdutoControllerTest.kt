package com.pan.apiprodutos.component

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest()
@ActiveProfiles("test")
class ProdutoControllerTest {

    @Test
    fun `when activating inactive product return active one`(){
        ProductComponent.createMockActivateProductRequest("1",ProductComponent.createInactiveProduct())

    }
}