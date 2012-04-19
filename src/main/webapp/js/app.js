var App = Em.Application.create();

App.MyView = Em.View.extend({
  mouseDown: function() {
//    window.alert("hello world!");
  }
});

Address = Em.Object.extend({
    street: null,
    number: null
});

User = Em.Object.extend({
    firstName: null,
    lastName: null,
    maidenName: null,
    married: null,
    dob: null,
    numberOfAddressesVals: [1,2,3,4,5,6,7,8,9,10],
    numberOfAddresses: 1,
    addresses: [],

    numberOfAddressesChanged: function() {

        for (var i = 0; i < this.get("numberOfAddresses"); i++) {
            this.get("addresses")[i] = Address.create({
                street: i + ' St',
                number: i
            });
        }
    }.observes("numberOfAddresses"),

    fullName: function() {
        return this.get("firstName") + ' ' + this.get("lastName");
    }.property("firstName", "lastName")
});

App.user = User.create();

$.getJSON("/ember-poc-1/user/1", function(data) {
    App.user.set("firstName", data.firstName);
    App.user.set("lastName", data.lastName);
    App.user.set("maidenName", data.maidenName);
    App.user.set("married", data.married);
    App.user.set("dob", data.dob);
});