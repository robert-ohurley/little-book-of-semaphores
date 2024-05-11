package main

import (
	"fmt"
	"sync"
)

var wg = sync.WaitGroup{}
var sharedResource = 0

type ThreadA struct {
	m *sync.Mutex
}

func (t *ThreadA) increment() {
	wg.Add(1)
	go func() {
		t.m.Lock()
		sharedResource++
		t.m.Unlock()
		wg.Done()
	}()
}

type ThreadB struct {
	m *sync.Mutex
}

func (t *ThreadB) increment() {
	wg.Add(1)
	go func() {
		t.m.Lock()
		sharedResource++
		t.m.Unlock()
		wg.Done()
	}()
}

func main() {
	mut := sync.Mutex{}
	tA := ThreadA{&mut}
	tB := ThreadB{&mut}

	for i := 0; i < 10; i++ {
		tA.increment()
		tB.increment()
	}

	fmt.Println(sharedResource)
}
