from xa.model.Operation import Operation


class Action(Operation):

    def __init__(self, eventName, value=0, timestamp=None):
        self.eventName = eventName
        self.value = value
        self.timestamp = timestamp

    def getOperationName(self, index):
        return 'action' + str(index)

    def getOperationValue(self):
        value = self.eventName + ',' + str(self.value)
        if None != self.timestamp:
            value += ',' + str(self.timestamp)
        return value

