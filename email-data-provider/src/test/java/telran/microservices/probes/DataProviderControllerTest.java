package telran.microservices.probes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import telran.microservices.probes.dto.EmailData;
import telran.microservices.probes.service.DataProvider;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
public class DataProviderControllerTest {
    private static final long SENSOR_ID_NORMAL = 123;
    private static final long SENSOR_ID_NOT_EXIST = 124;
    private static final String ERROR_MESSAGE = "No email data for sensor with id "+ SENSOR_ID_NOT_EXIST;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    DataProvider dataProvider;
    @Autowired
    ObjectMapper objectMapper;
    EmailData emailData = new EmailData(new String[] {"vasya@gmail.com", "petya@gmail.com"},
            new String[]{"vasya", "petya"});
    @Test
    void dataExistTest() throws Exception{
        when(dataProvider.getEmailData(SENSOR_ID_NORMAL)).thenReturn(emailData);
        String jsonEmailData = mockMvc.perform(get("http://localhost:8080/email/data/" + SENSOR_ID_NORMAL))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String jsonExpected = objectMapper.writeValueAsString(emailData);
        assertEquals(jsonExpected, jsonEmailData);
    }
    @Test
    void dataNotExistTest() throws Exception {
        when(dataProvider.getEmailData(SENSOR_ID_NOT_EXIST)).thenThrow(new IllegalArgumentException(ERROR_MESSAGE));
        String response = mockMvc.perform(get("http://localhost:8080/email/data/" + SENSOR_ID_NOT_EXIST))
                .andDo(print()).andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
        assertEquals(ERROR_MESSAGE, response);
    }
}
