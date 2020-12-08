# Lab 1 – API service to upload images.

### Description:
Create Restful service to upload an image and another to show all the available images store in a bucket.

### Overview:
Using spring framework you will have to create two services one to upload an image and another to show it, take in count the best design pattern since you’ll be challenged to make different implementations to work with cloud providers.

#### Number 1:

In this part you must create these services using only h2 and your local file system, it is recommended to use spring profiles along with your solutions because later these services will run under docker containers.

Create a POST service to upload an image with the following constrains:

For Payload:

{
“name”:”screenshot1”
}


For Image:

only JPG format is permitted. (for security check its metadata)
less than 1M in size.

Exceptions:

 * If the image already registered, notify the client about it
 * If the image violates the previous constrains, then handle exception
 
Use the following response body to handle exceptions.
{
“status”:4XX,
“message”:”your message”
}

And create a GET service to show the image previously created


