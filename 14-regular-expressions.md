<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [Chapter 14: Regular Expressions](#chapter-14-regular-expressions)
  - [Learning Objectives](#learning-objectives)
  - [14.1. What is a regular expression?](#141-what-is-a-regular-expression)
  - [14.2. Some Useful Regex Symbols with Examples](#142-some-useful-regex-symbols-with-examples)
  - [14.3. Repeating Patterns with Quantifiers](#143-repeating-patterns-with-quantifiers)
  - [14.4. Example: A Pattern for Java Instance Variable Names](#144-example-a-pattern-for-java-instance-variable-names)
  - [14.5. Special symbols](#145-special-symbols)
  - [14.6. Regex in Java](#146-regex-in-java)
  - [14.7. What Can We Do with Regular Expressions?](#147-what-can-we-do-with-regular-expressions)
  - [14.8. Exercises](#148-exercises)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# Chapter 14: Regular Expressions

## Learning Objectives

- Understand the basic syntax of regular expressions (regex).
- Determine whether a given string _matches_ a given regex.
- Apply regular expressions in Java.

## 14.1. What is a regular expression?

A regular expression (also called a “regex”) is a sequence of characters that specifies a pattern in text.
We say that a string _matches_ a regex if the string fits the pattern that the regex describes.

As an example, consider the regular expression `.*o`.
In this regex:
- the `.` matches any character except a line terminator by default in Java,
- the `*` indicates that the pattern requires zero or more occurrences of the preceding element (in this case, the `.`), and
- the `o` matches the character `o`.

The full regex matches a single-line string ending in `o`, such as `hello`, `ceo`, or `o`.

## 14.2. Some Useful Regex Symbols with Examples

We use `[` and `]` to define a choice of symbols. So `[abc]` will match "a", "b", or "c". 

By inserting a hyphen (`-`) we can specify a range. So `[a-z]` will match any lower case letter such as "a", "h", or "x". 

In Java's default regex mode, `[0-9]` and `\d` both match one ASCII digit.
`[a-zA-Z0-9]` matches one ASCII letter or digit.

`M[0-9][A-Z] [0-9][A-Z][0-9]` checks the basic format of a Toronto postal code,
such as `M0A 1B2`. It can't tell you whether the code is assigned to an address.

## 14.3. Repeating Patterns with Quantifiers

**Quantifiers** specify how many times the preceding element in a regex must occur for the pattern to match.
These quantifiers are summarized in the table below:

| Quantifier | meaning                    | Example                                       |
|------------|----------------------------|-----------------------------------------------|
| `*`        | zero or more               | `a*` matches the empty string, `a`, `aa`, ... |
| `+`        | one or more                | `a+` matches `a`, `aa`, ...                   |
| `?`        | zero or one                | `a?b` matches `ab` or `b`                     |
| `{x}`      | `x` copies                 | `[ab]{2}` matches `aa`, `ab`, `ba`, or `bb`   |
| `{x,}`     | `x` or more copies         | `[a]{2,}` matches `aa`, `aaa`, ...            |
| `{x,y}`    | between `x` and `y` copies | `[a]{2,4}` matches `aa`, `aaa`, or `aaaa`     |

### 14.3.1. Character Classes

A **character class** is a set of characters, like `[a-z]`, that specifies which single character can occur.
Exactly one character from the set must occur.

> We can then apply quantifiers from the previous section to
specify repetitions and create more interesting patterns.

You can make your own character classes by using square brackets like
`[q-z]` and `[AEIOU]`, or you can use a predefined character class.

These character classes use Java's default regex mode:

| character class | description                      |
|-----------------|----------------------------------|
| `.`             | any character except a line terminator |
| `\d`            | a digit `[0-9]`                  |
| `\D`            | a non-digit `[^0-9]`             |
| `\s`            | a whitespace character           |
| `\S`            | a non-whitespace character       |
| `\w`            | a word character `[a-zA-Z_0-9]`  |
| `\W`            | a non-word character             |

> Note that the definition of `\w` includes the `_` character.

### 14.3.2. Groups and Matching Strings with Repeated Characters
While quantifiers allow us to repeat patterns, we may also want to repeat the _exact same_ matching characters.
To do this, we need to specify a **group** with `(` and `)`.

For example, suppose that we want a regex that will match any phone number of the form `xxx-xxx-xxxx` whose first three digits are all the same,
such as "**222**-987-5406", "**000**-232-3232", "**111**-111-1114".
The following regex uses a group to accomplish this:

`([0-9])\1\1-[0-9]{3}-[0-9]{4}`

`\1` is a **backreference**: it matches the exact text captured by group 1.
Here the group captures one digit, and the two backreferences require that same
digit twice more.

### 14.3.3. Multiple Groups

Capturing groups are numbered by the order of their opening parentheses, starting at 1.
For this reason, the only group in the above example was group `1`, which we wrote as `\1` when we wanted to refer to it later in the regex.

For example, `(([de])f)\2\1` will repeat both groups. The strings that match are:
"dfddf" and "efeef".

- Group 1 corresponds to the `[de]f` part of the pattern

- Group 2 corresponds to the `[de]` part of the pattern

To visually make it easier to see the matches, we can write them space-delimited as:

`df d df` and `ef e ef` to clearly see the repetition of the groups.

## 14.4. Example: A Pattern for Java Instance Variable Names

IDEs like IntelliJ might describe the Java naming conventions for variables in this way:

```^[a-z][a-zA-Z0-9]*$```

In plain English, we might describe the purpose of this regex as:

“A string which matches this regex is consistent with the Java naming conventions for variables”

Let's look at the symbols in this regex to understand how it is expressing this idea!

The `^` character means that the pattern must start at the beginning of the string.
This is called an **anchor**.

In the leftmost set of brackets, we are given all lowercase letters to choose from,
so the first character must be one of these 26 possible letters.

`[a-zA-Z0-9]*` allows zero or more ASCII letters or digits after the first
character. The `$` anchor marks the end (see §14.4.1).

This checks a naming convention. `next_value` and `$value` are legal Java
identifiers that fail the pattern. `class` fits the pattern but is a reserved word.

`x`, `numStudents`, and `obj1` match. `Alphabet` and `2ab` fail.

> Which strings match: `z3333`, `aBcB041`, or `78a`?

<details>
<summary>Check your answer</summary>

`z3333` and `aBcB041` match. `78a` starts with a digit, so it fails.

</details>

### 14.4.1. What if There Are No Anchors?

`matches()` requires the whole input even without anchors. `find()` looks for a
matching substring. For the pattern `[abc]C[a-e][24680]*[A-Z]`:

| Input | `matches()` | `find()` |
| --- | --- | --- |
| `cCaA` | `true` | `true` |
| `ABCcCcAa1A23` | `false` | `true` (finds `cCcA`) |
| `cCa1A` | `false` | `false` (`1` is not in `[24680]`) |

By default, `^` matches the start of the input. `$` matches the end or the
position before a final line terminator. In `MULTILINE` mode they also match at
line boundaries. `\z` matches only the absolute end.

## 14.5. Special symbols

Certain characters have special meaning in regex to allow us to develop more rich expressions.

For example, `.` matches any character except a line terminator by default.

From working with files and strings, you might recall that the
backslash (`\`) escape character can be combined with certain other characters to represent
things like a newline character (`\n`) or a tab character (`\t`). This is also the case in regex.
Another example of this are the built-in character classes, like `\s` and `\d`.

As the first character inside square brackets, `^` has another meaning
besides being an anchor as we saw before: it matches any character _except_ the contents of the square brackets.

For example, `[^aeiouAEIOU]` matches anything that _isn’t_ a vowel.


### 14.5.1. Escaping the Meaning of a Symbol

Escape the period as `\.` to match a literal dot. For example,
`[0-9]\.[0-9]` matches `1.2` and `3.8`.

### 14.5.2. Logical operators

The `|` symbol separates alternatives: `cat|dog` matches `cat` or `dog`.
Inside a Java character class, `&&` intersects sets of characters.

For example `[a-t&&r-z]` would match only the strings "r", "s", and "t".

> Note: different implementations of regex may support slightly different logical operators.

## 14.6. Regex in Java

Java parses the string literal first. Double each backslash so the regex engine
receives the intended pattern:

| Match | Regex | Java string literal |
| --- | --- | --- |
| One digit | `\d` | `"\\d"` |
| A literal period | `\.` | `"\\."` |
| A literal backslash | `\\` | `"\\\\"` |

The `String` class contains methods such as `split`, `matches`, `replaceAll`, and `replaceFirst` which all make use of regex if
you read their documentation.

The [`Pattern`](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html) class is used to represent a regex.
It provides static methods `compile(String regex)` and `matches(String regex, String string)` which create a Pattern object
that represents the given regex string and matches a given regex string to a given string, respectively.
We encourage you to read the documentation linked above for this class, as it gives a nice summary of regex syntax in Java.

For example, the documentation for `Pattern` provides the following basic usage example:

```java
Pattern p = Pattern.compile("a*b");
Matcher m = p.matcher("aaaaab");
boolean b = m.matches();
```

The documentation highlights that `matches` provides a more convenient equivalent way to do the above:

```java
boolean b = Pattern.matches("a*b", "aaaaab");
```

A [`Matcher`](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Matcher.html)
tracks a search on one input. After a successful `matches()` or `find()`,
`group()` returns the whole match and `group(1)` returns the first capture.
Repeated calls to `find()` return successive non-overlapping matches. Reuse the
compiled `Pattern`, but create a fresh matcher for each input.

We also provide [`Demo.java`](code/src/main/java/regex/Demo.java) which contains additional examples.

## 14.7. What Can We Do with Regular Expressions?

As we have seen, regex provides concise and expressive ways to describe patterns in text.
They allow us to perform various tasks, such as:

- Find data in documents
  - Examples: phone numbers in a file, email addresses on a webpage, CSC course codes in text, or performing “Find and Replace” in IntelliJ.

- Validate input
  - Examples: checking if a variable name follows Java naming conventions or if a password meets complexity requirements.

Broadly, these tasks fall into two categories:
- Extraction: locating substrings that match a regex.
- Validation: determining whether an entire string matches a regex.

Regex is widely supported across programming languages and tools, making it an essential skill for developers.

## 14.8. Exercises

Both tasks are in the `ex18-regex` [exercise module](exercises/README.md).

- **Exercise 18a: Validators.** Use `String.matches` in
  [Validators.java](exercises/ex18-regex/src/main/java/Validators.java) to check
  a simplified email format, `NNN-NNN-NNNN` phone numbers, and ASCII identifier
  shape. Run
  [ValidatorsTest.java](exercises/ex18-regex/src/test/java/ValidatorsTest.java).
- **Exercise 18b: Extractor.** Use `Pattern` and `Matcher` in
  [Extractor.java](exercises/ex18-regex/src/main/java/Extractor.java) to find
  course codes, extract their three-digit numbers, and mask emails with `***`.
  Run
  [ExtractorTest.java](exercises/ex18-regex/src/test/java/ExtractorTest.java).

<details>
<summary>Exercise 18 solutions</summary>

18a. Pass each pattern to `String.matches`. Use the escaping rules in §14.6
when writing these patterns as Java string literals.

| Check | Pattern |
| --- | --- |
| Email | `[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}` |
| Phone | `\d{3}-\d{3}-\d{4}` |
| Identifier shape | `[A-Za-z_$][A-Za-z0-9_$]*` |

The identifier pattern allows digits only after the first character. It leaves
out Unicode identifiers and accepts reserved names such as `class` and `_`.

18b. In `[A-Z]{3}(\d{3})[A-Z]\d[A-Z]`, the parentheses capture the three-digit
course number. When `find()` locates `CSC207H1F`, `group()` gives the full code
and `group(1)` gives `207`. `EMAIL.matcher(text).replaceAll("***")` replaces each
email match while preserving the rest of the text.

```bash
mvn -P exercises -pl exercises/ex18-regex test
```

</details>
