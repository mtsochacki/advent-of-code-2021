# *Overriding hashCode* -  A Story In Several Paragraphs
The day is January 5th of 2022. The method of solving day13 requires Overriding equals() and hashCode() methods for the Point class. A discussion ensues. Is it necessary? Is it something that must be done? Would it work without it?

Hard work and frivolous testing began. Questions were asked. Answers were googled. Parentheses were sacrificed to the programming gods.

In the end, only the strongest survived. The strongest and the ones that overrode both those methods. Because as it stands out, it was not only absolutely necessary to do it, it would also be very rude to not do it.
So I did it. And then, when I confirmed that everything works I was tasked with another challenge.

In the pursuit of lightning speed and the neverending race to perfection, three hash functions were brought into the light from the annals of the past. The first one, a classic. Created by the enthusiasts of effective Java, it looked somewhat like this:
```java
int result = 17;
result = 31 * result + x;
result = 31 * result + y;
return result;
```
Then, another solution, very elegant in its simplicity, was called into action and it looked like this:
```java
return 100000 * x + y
```
Finally, for the fans of fast, furious and brutal, the final contender approached:
```java
return 0;
```
The three were tested by the scorching sun on the Sahara desert, by the neverending depths of Mariana Trench, by the frozen wastes of arctic poles and by the howling winds of...

Hmm...

Of some famous place where often wind is very strong! Unfortunately none of our chosen methods had the upper hand over the others. To decide this duel of fates all three contenders were tested in the most sophisticated, difficult to comprehend and certainly totally methodologically correct contest (and what I mean by that is running the solution to puzzle 13 in a loop for a 1000 times, calculating and then printing the total running time of the solution, repeating that process 10 times and then, finally, calculating the average).

| method | 17 & 31 | 10000 * x + y| 0 |
| :---: | :---: | :---: | :---: |
|time *(ms)* | 4709 |4799 | 56188 |

As we can see in the masterfully presented table above, there was no significant difference between the first two methods as they appear to generate reasonably unique values.

When it comes to the *return 0* method, it is considerably slower, which is understandable since all objects land in the same "bucket" and for each of them equals method needs to be called to be able to distinguish specific objects.