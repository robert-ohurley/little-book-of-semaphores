package main

import (
	"fmt"
	"sync"
	"time"
)

var wg = sync.WaitGroup{}

type ThreadA struct{}

func (t *ThreadA) task1() {
	go func() {
		time.Sleep(1 * time.Second)
		fmt.Println("Completed task A1")
		wg.Done()
	}()
}

func (t *ThreadA) task2() {
	go func() {
		time.Sleep(1 * time.Second)
		fmt.Println("Completed task A2")
		wg.Done()
	}()
}

type ThreadB struct{}

func (t *ThreadB) task1() {
	go func() {
		time.Sleep(1 * time.Second)
		fmt.Println("Completed task B1")
		wg.Done()
	}()
}

func (t *ThreadB) task2() {
	go func() {
		time.Sleep(1 * time.Second)
		fmt.Println("Completed task B2")
		wg.Done()
	}()
}

func main() {
	tA := ThreadA{}
	tB := ThreadB{}

	for {
		wg.Add(2)
		tA.task1()
		tB.task1()
		wg.Wait()

		wg.Add(2)
		tA.task2()
		tB.task2()
		wg.Wait()
	}
}
