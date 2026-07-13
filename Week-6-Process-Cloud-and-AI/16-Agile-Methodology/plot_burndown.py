"""
plot_burndown.py

Reads burndown-data.csv (day, ideal_remaining_points, actual_remaining_points)
and plots the ideal vs. actual sprint burndown lines, saving the chart to
burndown-chart.png in the same directory.

Usage:
    pip install -r requirements.txt
    python plot_burndown.py
"""

import os

import pandas as pd
import matplotlib.pyplot as plt

SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
DATA_FILE = os.path.join(SCRIPT_DIR, "burndown-data.csv")
OUTPUT_FILE = os.path.join(SCRIPT_DIR, "burndown-chart.png")


def main() -> None:
    df = pd.read_csv(DATA_FILE)

    fig, ax = plt.subplots(figsize=(8, 5))

    ax.plot(
        df["day"],
        df["ideal_remaining_points"],
        label="Ideal burndown",
        linestyle="--",
        marker="o",
        color="#4c72b0",
    )
    ax.plot(
        df["day"],
        df["actual_remaining_points"],
        label="Actual burndown",
        linestyle="-",
        marker="s",
        color="#dd8452",
    )

    ax.set_title("Sprint 1 Burndown — Online Bookstore")
    ax.set_xlabel("Sprint Day")
    ax.set_ylabel("Remaining Story Points")
    ax.set_xticks(df["day"])
    ax.set_ylim(bottom=0)
    ax.legend()
    ax.grid(True, alpha=0.3)

    fig.tight_layout()
    fig.savefig(OUTPUT_FILE, dpi=150)
    print(f"Saved chart to {OUTPUT_FILE}")


if __name__ == "__main__":
    main()
