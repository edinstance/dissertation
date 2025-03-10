import { SortDirection, Sorting } from "@/gql/graphql";
import { SetStateAction, useMemo } from "react";
import { Select } from "../ui/Select";

function ItemSorting({
  value,
  setItemSortValue,
}: {
  value: Sorting;
  setItemSortValue: React.Dispatch<SetStateAction<Sorting>>;
}) {
  const options = useMemo(
    () => [
      { value: "price:desc", label: "Price highest" },
      { value: "price:asc", label: "Price lowest" },
      { value: "ending_time:desc", label: "Ending soonest" },
      { value: "ending_time:asc", label: "Ending latest" },
      { value: "stock:desc", label: "Stock highest" },
      { value: "stock:asc", label: "Stock lowest" },
    ],
    [],
  );

  const valueString =
    value?.sortBy && value?.sortDirection
      ? `${value.sortBy}:${value.sortDirection.toLowerCase()}`
      : "";

  const selectedOption = useMemo(
    () => options.find((opt) => opt.value === valueString) || null,
    [options, valueString],
  );

  function onChange(option: { value: string | number; label: string }) {
    if (typeof option.value === "string") {
      const [sortBy, sortDirectionStr] = option.value.split(":");
      const sortDirection =
        sortDirectionStr?.toUpperCase() === "DESC"
          ? SortDirection.Desc
          : SortDirection.Asc;

      setItemSortValue({
        sortBy,
        sortDirection,
      });
    }
  }

  return (
    <Select
      options={options}
      value={selectedOption}
      onChange={onChange}
      placeholder="Sort by..."
      className="min-w-40"
    />
  );
}

export default ItemSorting;
