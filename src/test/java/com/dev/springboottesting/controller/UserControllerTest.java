package com.dev.springboottesting.controller;

import com.dev.springboottesting.dto.UserDto;
import com.dev.springboottesting.entity.User;
import com.dev.springboottesting.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONValue;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final String URI = "/api/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){

    }

    @DisplayName("Test Add User Function")
    @Test
    void whenGivenUserObject_ReturnString_CreatedUser() throws Exception {
        
        UserDto user = UserDto.builder()
                .firstName("Jasmine")
                .lastName("Mary")
                .email("jasmine@mary.com")
                .password("Axaskm.@97")
                .build();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URI+"/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Created new user");
    }

    @DisplayName("Test GetAllEmployee Function")
    @Test
    void whenGetAllEmployee_thenReturnListOfEmployee() throws Exception {

        /*
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI+"/all").accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status); */

        List<User> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(User.builder().firstName("abcd").lastName("efgh").email("abcd@efgh.com").build());
        listOfEmployees.add(User.builder().firstName("efgh").lastName("ijkl").email("efgh@gmail.com").build());

        given(userService.getAllUsers()).willReturn(listOfEmployees);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(URI+"/all"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }

    @Test
    void findUserById() throws Exception {
        Long id = 13L;
        User expectedUser = User.builder()
                .id(id)
                .firstName("abc")
                .lastName("efdfg")
                .email("abcd@efg.com")
                .password("abcdE.5@f")
                .build();

        Mockito.when(userService.findUserById(id)).thenReturn(expectedUser);

        ResultActions resultActions = (ResultActions) mockMvc.perform(MockMvcRequestBuilders.get(URI+"/{id}", id));

        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(expectedUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedUser.getLastName())))
                .andExpect(jsonPath("$.email", is(expectedUser.getEmail())))
                .andExpect(jsonPath("$.password", is(expectedUser.getPassword())));


        /*
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI+"/{id}", id)).andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedUser), actualResponse); */
    }

    @Test
    void delete() throws Exception {
        Long id = 12L;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(URI+"/delete/{id}", id)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();

        Map<String, String> map = new HashMap<>();
        map.put("message", "success");

        String jsonStr = JSONValue.toJSONString(map);

        assertEquals(jsonStr, content);
    }

    @Test
    void update() throws Exception{
        
    }
}