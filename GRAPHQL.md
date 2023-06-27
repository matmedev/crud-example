```graphql
query {
    restaurants {
     id
     name
     isOnWolt
    }
}
```

```graphql
query {
    restaurant(id: "649a8405284763128d011de8") {
        id
        name
        lastModifiedDate
    }
}
```

```graphql
mutation {
    createRestaurant(restaurant: {
        name: "Graph Restaurant",
        address: "Address 1234 6/7",
        phoneNumber: "12345678",
        isVegan: false
    }) {
        id
        name
        createdDate
    }
}
```

```graphql
mutation {
    updateRestaurant(id: "649a8405284763128d011de8", restaurant: {
        name: "Graph Restaurant Mutated"
        address: "Address 1234 6/7",
        phoneNumber: "12345678",
    }) {
        id
        name
        createdDate
        lastModifiedDate
    }
}
```

```graphql
mutation {
    deleteRestaurant(id: "649a8481284763128d011dea") {
        id
        name
    }
}
```
