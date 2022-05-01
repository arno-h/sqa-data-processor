This program parses a file ("data.txt") that has time stamps for different activities.
It calculates the duration of each activity and sums up durations of same activities.
If an activity was started on a holiday, the duration is counted twice.


Task 1:
Refactor DataProcessor: File input and System.out are hard to test.

Task 2:
Add test cases for 100% path coverage of DataProcessor::sumDuration() function.

Task 3:
Add two relevant test cases for DataProcessor::calcDuration() that use mocks in order
to test the doubling of the duration on holidays.

Task 4:
Add two relevant test cases for DataProcessor::process().