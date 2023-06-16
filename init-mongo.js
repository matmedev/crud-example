db.createUser(
    {
        user: "restaurant_app_user",
        pwd: "restaurant_app_pass",
        roles: [ { role: "readWrite", db: "restaurant_app" } ]
    }
)
