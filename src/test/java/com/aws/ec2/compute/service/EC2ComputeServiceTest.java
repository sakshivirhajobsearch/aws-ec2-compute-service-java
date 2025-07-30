package com.aws.ec2.compute.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.InstanceState;
import software.amazon.awssdk.services.ec2.model.InstanceStateName;
import software.amazon.awssdk.services.ec2.model.InstanceType;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StartInstancesResponse;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesResponse;

public class EC2ComputeServiceTest {

	@Mock
	private Ec2Client mockEc2Client;

	private EC2ComputeService service;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		service = new EC2ComputeService(mockEc2Client); // we'll add this constructor below
	}

	@Test
	public void testListInstances() {
		Instance mockInstance = Instance.builder().instanceId("i-1234567890abcdef0").instanceType(InstanceType.T2_MICRO)
				.state(InstanceState.builder().name(InstanceStateName.RUNNING).build()).build();

		Reservation mockReservation = Reservation.builder().instances(mockInstance).build();

		DescribeInstancesResponse mockResponse = DescribeInstancesResponse.builder().reservations(mockReservation)
				.build();

		when(mockEc2Client.describeInstances()).thenReturn(mockResponse);

		service.listInstances(); // should print to console (mocked data)
		verify(mockEc2Client).describeInstances();
	}

	@Test
	public void testStartInstance() {
		String instanceId = "i-abcdef123456";

		StartInstancesResponse mockResponse = StartInstancesResponse.builder().build();
		when(mockEc2Client.startInstances(any(StartInstancesRequest.class))).thenReturn(mockResponse);

		service.startInstance(instanceId);
		verify(mockEc2Client).startInstances(any(StartInstancesRequest.class));
	}

	@Test
	public void testStopInstance() {
		String instanceId = "i-abcdef123456";

		StopInstancesResponse mockResponse = StopInstancesResponse.builder().build();
		when(mockEc2Client.stopInstances(any(StopInstancesRequest.class))).thenReturn(mockResponse);

		service.stopInstance(instanceId);
		verify(mockEc2Client).stopInstances(any(StopInstancesRequest.class));
	}
}
