package com.hasanozgan.demo.services;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import com.hasanozgan.demo.actors.CountingActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import javax.annotation.PostConstruct;

import java.util.concurrent.TimeUnit;

import static com.hasanozgan.demo.actors.CountingActor.Count;
import static com.hasanozgan.demo.actors.CountingActor.Get;
import static akka.pattern.Patterns.ask;

import static com.hasanozgan.demo.actors.config.SpringExtension.SpringExtProvider;

/**
 * Created with IntelliJ IDEA.
 * User: hasan.ozgan
 * Date: 4/17/14
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CounterService {

    @Autowired
    ActorSystem system;

    private ActorRef counter;

    @PostConstruct
    protected void initActors() {
        counter = system.actorOf(SpringExtProvider.get(system).props("CountingActor"), "counter");
    }

    public int get() {
        FiniteDuration duration = FiniteDuration.create(3, TimeUnit.SECONDS);
        Future<Object> result = ask(counter, new CountingActor.Get(), Timeout.durationToTimeout(duration));
        try {
            return (Integer) Await.result(result, duration);
        }
        catch (Exception ex) {
            return 0;
        }
    }

    public void increase() {
        counter.tell(new Count(), null);
    }

}
