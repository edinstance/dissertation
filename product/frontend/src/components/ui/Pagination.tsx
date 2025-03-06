import { cn } from "@/lib/utils";
import { ChevronLeftIcon, ChevronRightIcon } from "@heroicons/react/24/outline";

function Pagination({
  total,
  page,
  setCurrentPage,
}: {
  total: number;
  page: number;
  setCurrentPage: (page: number) => void;
}) {
  function handlePageChange(displayPageNumber: number) {
    const zeroIndexedPage = displayPageNumber - 1;
    if (zeroIndexedPage >= 0 && zeroIndexedPage < total) {
      setCurrentPage(zeroIndexedPage);
    }
  }

  const displayPage = page + 1;

  const renderTruncatedPageButtons = () => {
    const maxButtonsToShow = 5;
    const buttons = [];

    if (total <= maxButtonsToShow) {
      for (let i = 1; i <= total; i++) {
        buttons.push(
          <button
            key={i}
            onClick={() => handlePageChange(i)}
            className={cn("h-8 w-8 rounded border", {
              "border-blue-500 bg-blue-500 text-white": displayPage === i,
              "border-gray-300 hover:bg-blue-100 dark:hover:bg-blue-900":
                displayPage !== i,
            })}
          >
            {i}
          </button>,
        );
      }
    } else {
      buttons.push(
        <button
          key={1}
          onClick={() => handlePageChange(1)}
          className={cn("h-8 w-8 rounded border", {
            "border-blue-500 bg-blue-500 text-white": displayPage === 1,
            "border-gray-300 hover:bg-blue-100 dark:hover:bg-blue-900":
              displayPage !== 1,
          })}
        >
          1
        </button>,
      );

      let startPage = Math.max(
        2,
        displayPage - Math.floor((maxButtonsToShow - 2) / 2),
      );
      const endPage = Math.min(total - 1, startPage + maxButtonsToShow - 3);

      if (endPage - startPage < maxButtonsToShow - 3) {
        startPage = Math.max(2, endPage - (maxButtonsToShow - 3));
      }

      if (startPage > 2) {
        buttons.push(
          <span key="ellipsis-start" className="px-1">
            ...
          </span>,
        );
      }

      for (let i = startPage; i <= endPage; i++) {
        buttons.push(
          <button
            key={i}
            onClick={() => handlePageChange(i)}
            className={cn("h-8 w-8 rounded border", {
              "border-blue-500 bg-blue-500 text-white": displayPage === i,
              "border-gray-300 hover:bg-blue-100 dark:hover:bg-blue-900":
                displayPage !== i,
            })}
          >
            {i}
          </button>,
        );
      }

      if (endPage < total - 1) {
        buttons.push(
          <span key="ellipsis-end" className="px-1">
            ...
          </span>,
        );
      }

      buttons.push(
        <button
          key={total}
          onClick={() => handlePageChange(total)}
          className={cn("h-8 w-8 rounded border", {
            "border-blue-500 bg-blue-500 text-white": displayPage === total,
            "border-gray-300 hover:bg-blue-100 dark:hover:bg-blue-900":
              displayPage !== total,
          })}
        >
          {total}
        </button>,
      );
    }

    return buttons;
  };

  return (
    <div className="flex items-center justify-center gap-2 pb-4 pt-8">
      <button
        onClick={() => handlePageChange(displayPage - 1)}
        disabled={page === 0}
        className="h-8 w-8 text-gray-900 disabled:text-gray-400"
      >
        <ChevronLeftIcon />
      </button>

      {renderTruncatedPageButtons()}

      <button
        onClick={() => handlePageChange(displayPage + 1)}
        disabled={page === total - 1}
        className="h-8 w-8 text-gray-900 disabled:text-gray-400"
      >
        <ChevronRightIcon />
      </button>
    </div>
  );
}

export default Pagination;
