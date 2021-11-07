LRU & LFU Cache
--------------------------------------------------------------------

- An in-memory cache (for caching Objects) with configurable max size and eviction strategy.
- Two strategies should be implement: LRU and LFU.
- It is assumed that only one thread will access the cache, so there is no need to make it thread-safe.
- Unit tests to provide an example of usage of the cache

### Least Recently User (LRU) Cache:

<---- to delete  <----
 * head (elem) ...(elem)...(elem)...(elem)...(elem) tail

LRU Cache contains:
* Map<value, Node>
* Node {value, previous element, next element}
* Node tail
* Node head

#### get()

* get elem
* move to tail (changing the pointers on previous and next elements for the current element and tail nodes)

#### put()

* is an element exists? - get()
* is the cache is full? - delete head
* create a new element and put it to tail

### Least Frequently User (LFU) Cache:

LFU Cache contains:
- Node with count of uses
- Map <Integer, LinkedHashSet<Object>> frequency;
- key: count of uses
- value: LinkedHashSet which save the order
- minFreq - minimal count of uses in cache


#### put()

* is the cache is full? - remove from map frequency the first value element with key equals minFreq
* when linkedHashMap for the frequency becomes null, set minFreq equals to next key from the map
* when put a new element, freqMin becomes 0
