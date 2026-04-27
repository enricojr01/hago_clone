# DESIGN NOTES
## No. 1 
Foreign Keys are nullable by default such that things can exist without
references to other fields. This was done to simplify working with raw SQL.
I honestly don't know any better way to handle things right now. --enrico

## No. 2
The `TimeSlotBean` class uses LocalTime on the start and end fields
to represent an apppointment slot at a clinic, but the JDBC requires a Time value,
and MySQL uses a TIME datatype formatted as "hhh:mm:ss". 

To use, convert LocalTime to Time like so: 
```java
// create a LocalTime instance. Note that
// the TimeSlotBean does not currently check// to see if the start time comes before the
// end time or vice versa.
LocalTime start = LocalTime.parse("09:00");

// convert to time for use in prepared statements to insert into database ...
Time forQuery = Time.valueOf(start);

// ...and back to local time when fetching from database
forQuery.toLocalTime();
```

## No. 3
Bcrypt (`at.favre.lib.Bcrypt`) is used to hash and verify passwords because
I didn't want to look like a complete hack. 

When creating employee/patient accounts, the password needs to be hashed like so:
```java
// pword will come from a form input or something
String pword = "unhashed password";
String hashed = Bcrypt.withDefaults().hashToString(12, pword.toCharArray()
// then store hashed in the database
```

When verifying passwords, i.e on login:
```java
// Fetch the employee / patient Bean, whichever
EmployeeBean eb = ...;
String pword = "unhashed password"
char[] pass = pword.toCharArray();
char[] hash = eb.getPassword().toCharArray();

Bcrypt.Result result = Bcrypt.verifyer().verify(pass, hash);

if (result.verified == true) {
	// handle login here
} else {
	// reject with error here
}
```

## No 4.
The add / edit / delete flow for servlets is a bit tedious but at least its standard:

```
beanServlet?action=addDisplay -> FORWARDS TO add.jsp, add.jsp calls
beanServlet?action=addSave -> saves to database then REDIRECTS TO
beanServlet?action=addSuccess&id=<n>&name=<n>.... -> FORWARDS TO addSuccess.jsp
```

This prevents client-side refreshes from "replaying" the add action. Bean details are passed
in the query parameters (as needed) because we need to display them for confirmation, etc.

The edit flow looks similar:
```
beanServlet?action=editDisplay&id=<n> -> FORWARDS TO edit.jsp, edit.jsp calls
beanServlet?action=editSave&id=<n> -> saves to database then REDIRECTS to
beanServlet?action=editSuccess&id=<n>&name=<n>&.... -> FORWARDS TO editSuccess.jsp
```

The delete flow has a confirmation step, as per requirements:
```
beanServlet?action=deleteConfirm&id=<n> -> FORWARDS TO deleteConfirm.jsp, which then calls
beanServlet?action=delete&id=<n> -> deletes the bean then REDIRECTS TO
beanServlet?action=deleteSuccess&id=<n>&... -> FORWARDS TO deleteSuccess.jsp
```

## No 5.
I get an Illegal State Exception (WELD-000227) almost every time I change something in the code, after the
hot deployment completes. 

Possible fix here:
https://stackoverflow.com/questions/36104833/org-jboss-weld-exceptions-illegalstateexception-weld-000227-after-every-change
