// program to calculate mean of an individual series
#include<iostream.h>

int main()
{
	// Declaration of X and n
	int X[50];
	int n;
	
	//Entering value of n
	cout<<"How may elements are there? ";
	cin>>n;
	
	//Entering Values in Array
	cout<<"Enter elements : ";
	for(int i=0;i<n;i++){
		cin>>X[i];
	}
	
	// Finding  ΣX
	int sum=0;
	for(int i=0;i<n;i++){
		sum+=X[i];
	}
	
	//Calculating mean
	float mean=(float)sum/n;
	
	//Priting mean
	cout<<"Mean of the series is : "<<mean;
	
	return 0;
}
