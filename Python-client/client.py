import requests

# Set endpoint
enpoint = "http://localhost:8080/REST-Server/"
# Set content-type and accept to json
headers = {'content-type': 'application/json', 'accept': 'application/json'}


class AdminService:
    """
    Class to interact with the admin service
    """
    global enpoint
    global headers

    def __init__(self):
        # Set endpoint
        self.endpoint = enpoint+"admin/"

    def getlist(self):
        # Do request
        r = requests.get(self.endpoint+'booking/list', headers=headers)
        # Convert to json
        data = r.json()
        # Loop the list
        for booking in data:
            # Print each
            print(booking)


adminservice = AdminService()

adminservice.getlist()
