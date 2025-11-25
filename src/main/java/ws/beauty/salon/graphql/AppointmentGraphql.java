package com.example.demo.graphql;

import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.demo.dto.AppointmentRequest;
import com.example.demo.dto.AppointmentResponse;
import com.example.demo.service.AppointmentService;

@Controller
@RequiredArgsConstructor
public class AppointmentGraphQLController {

    private final AppointmentService appointmentService;

    

    @QueryMapping
    public List<AppointmentResponse> appointments(
            @Argument Integer page,
            @Argument Integer size
    ) {
        if (page != null && size != null) {
            return appointmentService.findAll(page, size);
        }
        return appointmentService.findAll();
    }

    @QueryMapping
    public AppointmentResponse appointmentById(@Argument Integer id) {
        return appointmentService.findById(id);
    }

   
    @QueryMapping
    public List<AppointmentResponse> appointmentsByDateRange(
            @Argument
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String start,
            @Argument
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String end
    ) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return appointmentService.findByDateRange(startDate, endDate);
    }

   

    @MutationMapping
    public AppointmentResponse createAppointment(@Argument AppointmentRequest input) {
        return appointmentService.create(input);
    }

    @MutationMapping
    public AppointmentResponse updateAppointment(
            @Argument Integer id,
            @Argument AppointmentRequest input
    ) {
        return appointmentService.update(id, input);
    }

    @MutationMapping
    public Boolean deleteAppointment(@Argument Integer id) {
        appointmentService.delete(id);
        return true;
    }
}
