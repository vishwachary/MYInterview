So, why not just define getXmlFragment() separately in each dialog class?

You could, but you'd lose several key benefits:

1. Consistency

Without a common base, there's no guarantee each dialog will even have getXmlFragment().

By forcing it via an abstract method, you make sure every dialog class must implement it.

2. Polymorphism

Let's say you have:
List<BaseDialog> dialogs = getDialogs();
for (BaseDialog dialog : dialogs) {
    StringBuilder xml = dialog.getXmlFragment();  // Works for all dialogs
}
If you didn't have BaseDialog, you'd have to do:
if (dialog instanceof TestDialog) {
    ((TestDialog) dialog).getXmlFragment();
} else if (dialog instanceof ExampleDialog) {
    ((ExampleDialog) dialog).getXmlFragment();
}
which is messy, not scalable, and defeats the purpose of OOP.

3. Shared Behavior

If BaseDialog also provides common behavior (like showDialog() or layout management), all child dialogs can inherit and reuse it.
Why Make BaseDialog abstract?

You make it abstract because:

You don't want someone to create a BaseDialog directly.

It doesn't make sense without a specific implementation of getXmlFragment() (and maybe other methods).

Abstract class allows you to have both:

Abstract methods (must be overridden)

Concrete methods (optional to override)
