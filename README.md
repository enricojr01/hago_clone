# DESIGN NOTES
## No. 1 
Foreign Keys are nullable by default such that things can exist without
references to other fields. This was done to simplify working with raw SQL.
I honestly don't know any better way to handle things right now. --enrico