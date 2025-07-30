package com.aws.ec2.compute.service;

import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;

public class EC2ComputeService {

	private final Ec2Client ec2Client;

	// ✅ Constructor to allow injection (used by your test)
	public EC2ComputeService(Ec2Client ec2Client) {
		this.ec2Client = ec2Client;
	}

	// ✅ Optional default constructor (if needed for production use)
	public EC2ComputeService() {
		this(Ec2Client.create());
	}

	// ✅ List all instances
	public void listInstances() {
		
		DescribeInstancesResponse response = ec2Client.describeInstances();
		for (Reservation reservation : response.reservations()) {
			for (Instance instance : reservation.instances()) {
				System.out.println("Instance ID: " + instance.instanceId());
				System.out.println("Instance Type: " + instance.instanceType());
				System.out.println("State: " + instance.state().nameAsString());
				System.out.println("--------");
			}
		}
	}

	// ✅ Start an instance
	public void startInstance(String instanceId) {
		
		StartInstancesRequest request = StartInstancesRequest.builder().instanceIds(instanceId).build();
		ec2Client.startInstances(request);
		System.out.println("Started instance: " + instanceId);
	}

	// ✅ Stop an instance
	public void stopInstance(String instanceId) {
		
		StopInstancesRequest request = StopInstancesRequest.builder().instanceIds(instanceId).build();
		ec2Client.stopInstances(request);
		System.out.println("Stopped instance: " + instanceId);
	}
}
