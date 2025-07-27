package com.aws.ec2.compute.service;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.ec2.model.StartInstancesRequest;
import software.amazon.awssdk.services.ec2.model.StopInstancesRequest;

public class EC2ComputeService {

	private final Ec2Client ec2;

	public EC2ComputeService() {
		// Set your AWS region here (e.g., US_EAST_1)
		this.ec2 = Ec2Client.builder().region(Region.US_EAST_1).credentialsProvider(DefaultCredentialsProvider.create())
				.build();
	}

	public void listInstances() {
		System.out.println("Listing EC2 Instances...");
		DescribeInstancesResponse response = ec2.describeInstances();
		for (Reservation reservation : response.reservations()) {
			for (Instance instance : reservation.instances()) {
				System.out.printf("Instance ID: %s | State: %s | Type: %s%n", instance.instanceId(),
						instance.state().name(), instance.instanceType());
			}
		}
	}

	public void startInstance(String instanceId) {
		System.out.println("Starting Instance: " + instanceId);
		StartInstancesRequest request = StartInstancesRequest.builder().instanceIds(instanceId).build();
		ec2.startInstances(request);
	}

	public void stopInstance(String instanceId) {
		System.out.println("Stopping Instance: " + instanceId);
		StopInstancesRequest request = StopInstancesRequest.builder().instanceIds(instanceId).build();
		ec2.stopInstances(request);
	}

	public static void main(String[] args) {
		EC2ComputeService service = new EC2ComputeService();

		service.listInstances(); // List all EC2 instances

		// Example usage: Uncomment to start/stop specific instance
		// service.startInstance("i-0abc123def456ghi7");
		// service.stopInstance("i-0abc123def456ghi7");
	}
}