# Rakuten
## full-stack-recruitment-test

UI Task
-------

1. Create an UI which allow to upload csv file containing employee information
	1. Employee csv contains the following columns: name, department, designation, salary, joining date
2. Show the uploaded Employee information in a table.
3. Table should have pagination capabilities.
4. Any updates in the employee information should be reflected when refreshing the table or page.
5. Provide link to download the file with error records

Server API Task
----------------

1. Expose service to consume uploaded file
2. Validation
3. Service to update the employee information

Validation
----------

Beside basic validation for the uploaded file.  
Following are the Validation for each field:  
__Name__ -> can only contain alphabets  
__Department__ -> alphanumeric with `-_*` as special characters  
__Designation__ -> Developer, Senior Developer, Manager, Team Lead, VP, CEO  
__Salary__ -> can only contain Numeric value  
__Joining Date__ -> yyyy-MM-dd format
 
 
The basic project structure is available from the below GitHub repo. https://github.com/rakutenAsia/full-stack-recruitment-test