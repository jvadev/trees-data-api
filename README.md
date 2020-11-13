# Trees Data Api

Api returns information about trees.

The controller method "/aggregated-info" makes an aggregated search of trees in the circle.

Input:
  - X and Y of the cartesian center point
  - A search radius in meters

Output:
  - Aggregation by "common name"

Example of the output:
```json
{
    "red maple": 30,
    "American linden": 1,
    "London planetree": 3
}
```

The service uses the data from the 3rd party API (https://data.cityofnewyork.us/Environment/2015-Street-Tree-Census-Tree-Data/uvpi-gqnh): `https://data.cityofnewyork.us/resource/nwxe-4ae8.json` to get trees list.

Due to heavy request the api uses distributed Hazelcast cache.
