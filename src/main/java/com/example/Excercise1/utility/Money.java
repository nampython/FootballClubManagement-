package com.example.Excercise1.utility;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** @deprecated */
public class Money implements Serializable, Comparable {
    private static final int US_CURRENCY_DEFAULT_FRACTION_DIGITS = 2;
    private static final RoundingMode ROUNDING_MODE;
    private static final RoundingMode ROUNDING_MODE_DOWN;
    private static final MathContext PRECISION;
    private static final BigDecimal PCT;
    private static final int PERCENTAGE_DEFAULT_FRACTION_DIGITS = 2;
    public static final double MAX_MONEY_AMT = 9.999999999E9;
    private long cents;
    private static final int CENTS_PER_DOLLAR = 100;
    public static final Money ZERO;

    static {
        ROUNDING_MODE = RoundingMode.HALF_UP;
        ROUNDING_MODE_DOWN = RoundingMode.DOWN;
        PRECISION = MathContext.DECIMAL64;
        PCT = new BigDecimal("100");
        ZERO = new Money(0L);
    }

    public Money() {
        this.cents = 0L;
    }

    public Money(long cents) {
        this();
        this.cents = cents;
    }

    public Money(double dollars) {
        this(Math.round(dollars * 100.0));
    }

    public Money(Money money) {
        this.cents = 0L;
        if (money == null) {
            this.cents = 0L;
        } else {
            this.cents = money.cents;
        }

    }

    public Money(String dollars) throws NumberFormatException {
        this();

        try {
            dollars = dollars.replace("$", "");
            dollars = dollars.replace(",", "");
            StringBuffer s = new StringBuffer(dollars);
            int index = dollars.indexOf(46);
            if (index < 0) {
                s.append("00");
            } else {
                int delta = s.length() - index;
                s.deleteCharAt(index);
                if (delta == 2) {
                    s.append('0');
                } else if (delta == 1) {
                    s.append("00");
                } else if (delta > 3) {
                    s.delete(s.length() - (delta - 3), s.length());
                }
            }

            this.cents = Long.parseLong(s.toString());
        } catch (NullPointerException var5) {
            this.cents = 0L;
        }

    }

    public void set(long cents) {
        this.cents = cents;
    }

    public void set(Money money) {
        if (money == null) {
            this.cents = 0L;
        } else {
            this.cents = money.cents;
        }

    }

    public void setDollars(double dollars) {
        this.cents = Math.round(dollars * 100.0);
    }

    public void zero() {
        this.set(0L);
    }

    /** @deprecated */
    public int asCents() {
        return this.intValue();
    }

    public long getCents() {
        return this.cents;
    }

    public float asDollars() {
        return this.floatValue();
    }

    public void add(long cents) {
        this.cents += cents;
    }

    public void add(Money money) {
        if (money != null) {
            this.cents += money.cents;
        }

    }

    public void add(double dollars) {
        this.add(new Money(dollars));
    }

    public void subtract(long cents) {
        this.cents -= cents;
    }

    public void subtract(Money money) {
        if (money != null) {
            this.subtract(money.cents);
        }

    }

    public void subtract(double dollars) {
        this.subtract(new Money(dollars));
    }

    public void multiply(long operand) {
        this.cents *= operand;
    }

    public Money difference(Money money) {
        return money != null ? new Money(this.cents - money.cents) : new Money(this.cents);
    }

    public String toString() {
        StringBuffer s = new StringBuffer(Long.toString(Math.abs(this.cents)));
        if (s.length() == 1) {
            s.insert(0, "0.0");
        } else if (s.length() == 2) {
            s.insert(0, "0.");
        } else {
            s.insert(s.length() - 2, '.');
        }

        if (this.cents < 0L) {
            s.insert(0, '-');
        }

        return s.toString();
    }

    public String toDisplayString() {
        StringBuffer s = new StringBuffer(this.toString());
        int count = s.length();

        for(int digits = this.cents < 0L ? 7 : 6; count > digits; count -= 3) {
            s.insert(count - 6, ',');
        }

        return s.toString();
    }

    public String toFormattedString() {
        StringBuilder s = new StringBuilder(this.toDisplayString());
        if (s.charAt(0) == '-') {
            s.insert(1, '$');
        } else {
            s.insert(0, '$');
        }

        return s.toString();
    }

    public int hashCode() {
        return (int)(this.cents ^ this.cents >>> 32);
    }

    public boolean equals(Object other) {
        return other != null && other instanceof Money && this.equals((Money)other);
    }

    public boolean equals(Money other) {
        return other != null && this.cents == other.getCents();
    }

    public boolean equalsNegative(Money other) {
        return other != null && this.cents * -1L == other.getCents();
    }

    /** @deprecated */
    public int intValue() {
        if (this.cents <= 2147483647L && this.cents >= -2147483648L) {
            return (int)this.cents;
        } else {
            throw new UnsupportedOperationException("The number of cents: " + this.cents + " cannot be converted to an int. The calling method should use longValue().");
        }
    }

    public long longValue() {
        return this.cents;
    }

    public float floatValue() {
        return Float.parseFloat(this.toString());
    }

    public double doubleValue() {
        return Double.parseDouble(this.toString());
    }

    public int compareTo(Object other) throws ClassCastException {
        return other == null ? 1 : this.compareTo((Money)other);
    }

    public int compareTo(Money other) {
        if (other == null) {
            return 1;
        } else if (this.cents < other.cents) {
            return -1;
        } else {
            return this.cents == other.cents ? 0 : 1;
        }
    }

    public boolean greaterThan(Money other) {
        return this.compareTo(other) > 0;
    }

    public boolean lessThan(Money other) {
        return this.compareTo(other) < 0;
    }

    public boolean isZero() {
        return this.cents == 0L;
    }

    public boolean isGreaterThanZero() {
        return this.cents > 0L;
    }

    public boolean isLessThanZero() {
        return this.cents < 0L;
    }

    public void negate() {
        this.cents *= -1L;
    }

    public Money getReverseAmt() {
        return new Money(this.cents * -1L);
    }

    public Money[] allocate(long[] ratios) throws IllegalArgumentException {
        if (ratios.length < 2) {
            throw new IllegalArgumentException("Insufficient allocation slots specified");
        } else {
            long total = 0L;

            int idx;
            for(idx = 0; idx < ratios.length; ++idx) {
                if (ratios[idx] < 0L) {
                    throw new IllegalArgumentException("Allocation ratio cannot be negative");
                }

                total += ratios[idx];
            }

            long absCents = Math.abs(this.cents);
            long remainder = absCents;
            Money[] results = new Money[ratios.length];

            for(idx = 0; idx < results.length; ++idx) {
                results[idx] = new Money(absCents * ratios[idx] / total);
                remainder -= results[idx].cents;
            }

            for(idx = 0; (long)idx < remainder; ++idx) {
                ++results[idx].cents;
            }

            if (this.cents < 0L) {
                for(idx = 0; idx < results.length; ++idx) {
                    results[idx].negate();
                }
            }

            return results;
        }
    }

    public List<Money> allocateWithNoPennyDistribution(List<Long> ratios) throws IllegalArgumentException {
        List<Money> resultList = new ArrayList();
        long remainder = 0L;
        if (!ratios.isEmpty()) {
            if (ratios.size() == 1) {
                resultList.add(new Money(this.cents));
            } else {
                long total = 0L;

                Long ratio;
                for(Iterator var8 = ratios.iterator(); var8.hasNext(); total += ratio) {
                    ratio = (Long)var8.next();
                    if (ratio < 0L) {
                        throw new IllegalArgumentException("Allocation ratio cannot be negative");
                    }
                }

                long absCents = Math.abs(this.cents);
                remainder = absCents;

                Iterator var10;
                Money proRatedMoney;
                for(var10 = ratios.iterator(); var10.hasNext(); remainder -= proRatedMoney.cents) {
                    ratio = (Long)var10.next();
                    proRatedMoney = total == 0L ? new Money(absCents / (long)ratios.size()) : new Money(absCents * ratio / total);
                    resultList.add(proRatedMoney);
                }

                if (this.cents < 0L) {
                    var10 = resultList.iterator();

                    while(var10.hasNext()) {
                        Money money = (Money)var10.next();
                        money.negate();
                    }

                    remainder *= -1L;
                }
            }
        } else {
            remainder = this.cents;
        }

        resultList.add(new Money(remainder));
        return resultList;
    }

    public Money addBD(Money money) {
        return new Money((new BigDecimal(this.toString())).add(new BigDecimal(money.toString()), PRECISION).setScale(2, ROUNDING_MODE).toString());
    }

    public Money subtractBD(Money money) {
        return new Money((new BigDecimal(this.toString())).subtract(new BigDecimal(money.toString()), PRECISION).setScale(2, ROUNDING_MODE).toString());
    }

    public Money discountBD(String disc) {
        return new Money((new BigDecimal(this.toString())).multiply(PCT.subtract(new BigDecimal(disc), PRECISION), PRECISION).divide(PCT, PRECISION).setScale(2, ROUNDING_MODE).toString());
    }

    public Money multiplyBD(double multiplicand) {
        return new Money((new BigDecimal(this.toString())).multiply(new BigDecimal(multiplicand), PRECISION).setScale(2, ROUNDING_MODE_DOWN).toString());
    }

    public Money percentageBD(String percentage) {
        return new Money((new BigDecimal(this.toString())).multiply(new BigDecimal(percentage), PRECISION).divide(PCT, PRECISION).setScale(2, ROUNDING_MODE_DOWN).toString());
    }

    public Money absoluteValue() {
        return new Money(Math.abs(this.cents));
    }

}

