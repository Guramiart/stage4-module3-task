package com.mjc.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsManagerAppTest {

    @Mock
    private SpringApplicationBuilder springApplicationBuilder;

    @Test
    void mainTest() {
        NewsManagerApp.main(new String[] {});
        assertTrue(true);
    }

    @Test
    void configureTest() {
        NewsManagerApp app = new NewsManagerApp();
        when(springApplicationBuilder.sources(NewsManagerApp.class)).thenReturn(springApplicationBuilder);
        SpringApplicationBuilder result = app.configure(springApplicationBuilder);
        verify(springApplicationBuilder).sources(NewsManagerApp.class);

        assertEquals(springApplicationBuilder, result);
    }

}
