import requests
import json
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

    def getBookingList(self):
        # Do request
        r = requests.get(self.endpoint+'booking/list', headers=headers)
        if r.status_code == 200:
            # Convert to json
            return r.json()
        return []

    def addCar(self, car):
        # Do request
        r = requests.put(self.endpoint+'add/car',
                         headers=headers, data=json.dumps(car))
        if r.status_code == 200 or r.status_code == 204:
            return True
        return False


adminservice = AdminService()

adminservice.getBookingList()

car = {
    "id": "99CE1524",
    "model": "BMW",
    "make": "M3",
    "color": "Black"
}

print(adminservice.addCar(car))
